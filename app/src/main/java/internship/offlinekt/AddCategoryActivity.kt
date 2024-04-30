package internship.offlinekt

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddCategoryActivity : AppCompatActivity() {

    lateinit var image: CircleImageView
    lateinit var camera:CircleImageView
    lateinit var name: EditText
    lateinit var submit: Button

    var IMAGE_SELECT_CODE = 123

    var appPermission = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    var appPermission33 = arrayOf(Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.POST_NOTIFICATIONS)
    private val PERMISSION_REQUEST_CODE = 1240

    var sSelectedPath = ""

    lateinit var apiInterface: ApiInterface
    lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
        apiInterface = ApiClient().getClient()!!.create(ApiInterface::class.java)

        image = findViewById(R.id.add_category_image)
        camera = findViewById(R.id.add_category_camera)
        name = findViewById(R.id.add_category_name)
        submit = findViewById(R.id.add_category_submit)

        camera.setOnClickListener {
            if (checkAndRequestPermission()) {
                selectImageMethod()
            }
        }

        submit.setOnClickListener {
            if(sSelectedPath.equals("")){
                CommonMethod().ToasFunction(this@AddCategoryActivity,"Please Select Image")
            }
            else if(name.text.toString().trim().equals("")){
                name.error = "Name Required"
            }
            else{
                if(ConnectionDetector(this@AddCategoryActivity).networkConnected()){
                    pd = ProgressDialog(this@AddCategoryActivity)
                    pd.setMessage("Please Wait...")
                    pd.setCancelable(false)
                    pd.show()
                    addCategoryData()
                }
                else{
                    ConnectionDetector(this@AddCategoryActivity).networkDisconnected()
                }
            }
        }

    }

    private fun addCategoryData() {
        //TODO("Not yet implemented")
        val namePart = RequestBody.create(MultipartBody.FORM, name.text.toString())

        val file = File(sSelectedPath)
        val filePart = MultipartBody.Part.createFormData(
            "catimage", file.name, RequestBody.create(
                MediaType.parse("image/*"), file
            )
        )

        val call = apiInterface.addCategoryData(namePart, filePart)
        call!!.enqueue(object : Callback<GetSignupData> {
            override fun onResponse(call: Call<GetSignupData>, response: Response<GetSignupData>) {
                pd.dismiss()
                if (response.code() == 200) {
                    if (response.body()!!.status!!) {
                        CommonMethod().ToasFunction(this@AddCategoryActivity,
                            response.body()!!.message!!
                        )
                        onBackPressed()
                    } else {
                        CommonMethod().ToasFunction(this@AddCategoryActivity,
                            response.body()!!.message!!
                        )
                    }
                } else {
                    CommonMethod().ToasFunction(this@AddCategoryActivity, "Server Error Code : " + response.code())
                }
            }

            override fun onFailure(call: Call<GetSignupData>, t: Throwable) {
                pd.dismiss()
                CommonMethod().ToasFunction(this@AddCategoryActivity, t.message.toString())
            }
        })
    }

    private fun checkAndRequestPermission(): Boolean {
        val listPermission: MutableList<String> = ArrayList()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            for (perm in appPermission33) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        perm
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    listPermission.add(perm)
                }
            }
            if (!listPermission.isEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    listPermission.toTypedArray<String>(),
                    PERMISSION_REQUEST_CODE
                )
                false
            } else {
                true
            }
        } else {
            for (perm in appPermission) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        perm
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    listPermission.add(perm)
                }
            }
            if (!listPermission.isEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    listPermission.toTypedArray<String>(),
                    PERMISSION_REQUEST_CODE
                )
                false
            } else {
                true
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val permissionResult = HashMap<String, Int>()
            var deniedCount = 0
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResult[permissions[i]] = grantResults[i]
                    deniedCount++
                }
            }
            if (deniedCount == 0) {
                selectImageMethod()
            } else {
                for ((permName, permResult) in permissionResult) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permName)) {
                        /*showDialogPermission("", "This App needs Read External Storage And Location permissions to work whithout and problems.",*/
                        showDialogPermission("",
                            "This App needs Read Storage permissions to work whithout and problems.",
                            "Yes, Grant permissions",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                checkAndRequestPermission()
                            },
                            "No, Exit app",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                finishAffinity()
                            },
                            false
                        )
                    } else {
                        showDialogPermission("",
                            "You have denied some permissions. Allow all permissions at [Setting] > [Permissions]",
                            "Go to Settings",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                val intent = Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", packageName, null)
                                )
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            },
                            "No, Exit app",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                finish()
                            },
                            false
                        )
                        break
                    }
                }
            }
        }
    }

    private fun selectImageMethod() {
        //TODO("Not yet implemented")
        FishBun.with(this@AddCategoryActivity)
            .setImageAdapter(GlideAdapter())
            .setMinCount(1)
            .setMaxCount(1)
            .setIsUseDetailView(false)
            .setActionBarColor(Color.parseColor("#795548"), Color.parseColor("#5D4037"), false)
            .setActionBarTitleColor(Color.parseColor("#ffffff"))
            .setButtonInAlbumActivity(false)
            .setReachLimitAutomaticClose(true)
            .setAllViewTitle("All")
            .setMenuAllDoneText("All Done")
            .setActionBarTitle(getResources().getString(R.string.app_name))
            .textOnNothingSelected("Please select three or more!")
            .startAlbumWithOnActivityResult(IMAGE_SELECT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_SELECT_CODE && resultCode == RESULT_OK && data != null) {
            val mSelected: List<Uri>? = data.getParcelableArrayListExtra(FishBun.INTENT_PATH)
            Log.d("RESPONSE_IMAGE_URI", mSelected!![0].toString())
            sSelectedPath = getImage(mSelected[0])
            Log.d("RESPONSE_IMAGE_PATH", sSelectedPath)
            image.setImageURI(mSelected[0])
        }
    }

    private fun getImage(uri: Uri): String {
        if (uri != null) {
            var path: String? = null
            val s_array = arrayOf(MediaStore.Images.Media.DATA)
            val c = managedQuery(uri, s_array, null, null, null)
            val id = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            if (c.moveToFirst()) {
                do {
                    path = c.getString(id)
                } while (c.moveToNext())
                //c.close();
                if (path != null) {
                    return path
                }
            }
        }
        return ""
    }

    private fun showDialogPermission(
        title: String,
        msg: String,
        positiveLable: String,
        positiveOnClickListener: DialogInterface.OnClickListener,
        negativeLable: String,
        negativeOnClickListener: DialogInterface.OnClickListener,
        isCancelable: Boolean
    ) : AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setCancelable(isCancelable)
        builder.setMessage(msg)
        builder.setPositiveButton(positiveLable, positiveOnClickListener)
        builder.setNegativeButton(negativeLable, negativeOnClickListener)
        val alertDialog = builder.create()
        alertDialog.show()
        return alertDialog
    }

}