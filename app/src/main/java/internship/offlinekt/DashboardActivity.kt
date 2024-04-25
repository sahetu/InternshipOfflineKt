package internship.offlinekt

import android.app.ProgressDialog
import android.batch1.MakeServiceCall
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    lateinit var email: TextView

    lateinit var logout: Button
    lateinit var profile: Button
    lateinit var deleteProfile: Button
    lateinit var userDataList: Button
    lateinit var userCustomList: Button
    lateinit var userRecyclerview: Button
    lateinit var myntraCat: Button
    lateinit var subCatTask: Button
    lateinit var activityFragment: Button
    lateinit var tabLayout: Button
    lateinit var bottomNav: Button
    lateinit var navDemo: Button
    lateinit var razorpayPayment: Button
    lateinit var dynamicCategory: Button
    lateinit var notification: Button

    lateinit var sp : SharedPreferences

    lateinit var db : SQLiteDatabase

    lateinit var apiInterface : ApiInterface
    lateinit var pd : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        apiInterface = ApiClient().getClient()!!.create(ApiInterface::class.java)

        db = openOrCreateDatabase("InternshipKt", MODE_PRIVATE,null)
        var tableQuery : String = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME VARCHAR(50),NAME VARCHAR(50),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(12),GENDER VARCHAR(6),CITY VARCHAR(50))"
        db.execSQL(tableQuery)

        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE)
        email = findViewById(R.id.dashboard_email)

        /*var bundle: Bundle = intent.extras!!
        //var sEmail : String = bundle.getString("EMAIL")!!
        val sEmail = bundle.getString("EMAIL")*/
        email.text = sp.getString(ConstantSp.NAME,"")

        userDataList = findViewById(R.id.dashboard_user_data_list)
        userDataList.setOnClickListener {
            CommonMethod().IntentFun(this@DashboardActivity,UserSimpleListActivity::class.java)
        }

        userCustomList = findViewById(R.id.dashboard_custom_user_data_list)
        userCustomList.setOnClickListener {
            CommonMethod().IntentFun(this@DashboardActivity,UserCustomListActivity::class.java)
        }

        myntraCat = findViewById(R.id.dashboard_recycler_myntra_category)
        myntraCat.setOnClickListener {
            CommonMethod().IntentFun(this@DashboardActivity,MyntraActivity::class.java)
        }

        userRecyclerview = findViewById(R.id.dashboard_recycler_data_list)
        userRecyclerview.setOnClickListener {
            CommonMethod().IntentFun(this@DashboardActivity,RecyclerviewDemoActivity::class.java)
        }

        logout = findViewById(R.id.dashboard_logout)
        logout.setOnClickListener {
            sp.edit().clear().commit()
            CommonMethod().IntentFun(this@DashboardActivity,MainActivity::class.java)
            finish()
        }

        deleteProfile = findViewById(R.id.dashboard_delete_profile)
        deleteProfile.setOnClickListener {
            /*var deleteQuery : String = "DELETE FROM USERS WHERE USERID='"+sp.getString(ConstantSp.USERID,"")+"'"
            db.execSQL(deleteQuery)
            sp.edit().clear().commit()
            CommonMethod().IntentFun(this@DashboardActivity,MainActivity::class.java)
            finish()*/
            if(ConnectionDetector(this@DashboardActivity).networkConnected()){
                /*doDelete(
                    this@DashboardActivity,
                    sp
                ).execute()*/
                pd = ProgressDialog(this@DashboardActivity)
                pd.setMessage("Please Wait...")
                pd.setCancelable(false)
                pd.show()
                doDeleteRetrofit()
            }
            else{
                ConnectionDetector(this@DashboardActivity).networkDisconnected()
            }
        }

        profile = findViewById(R.id.dashboard_profile)
        profile.setOnClickListener {
            CommonMethod().IntentFun(this@DashboardActivity,ProfileActivity::class.java)
        }

        activityFragment = findViewById(R.id.dashboard_recycler_activity_fragment)
        activityFragment.setOnClickListener {
            CommonMethod().IntentFun(this@DashboardActivity,ActivityToFragmentActivity::class.java)
        }

        tabLayout = findViewById(R.id.dashboard_recycler_tab_layout)
        tabLayout.setOnClickListener {
            CommonMethod().IntentFun(this@DashboardActivity,TabDemoActivity::class.java)
        }

        navDemo = findViewById(R.id.dashboard_recycler_nav)
        navDemo.setOnClickListener {
            CommonMethod().IntentFun(this@DashboardActivity,NavDemoActivity::class.java)
        }

        bottomNav = findViewById(R.id.dashboard_recycler_bottom_nav)
        bottomNav.setOnClickListener {
            CommonMethod().IntentFun(this@DashboardActivity,BottomNavActivity::class.java)
        }

        dynamicCategory = findViewById(R.id.dashboard_recycler_dynamic_category)
        dynamicCategory.setOnClickListener {
            CommonMethod().IntentFun(this@DashboardActivity,CategoryActivity::class.java)
        }

        razorpayPayment = findViewById(R.id.dashboard_payment)
        razorpayPayment.setOnClickListener {
            CommonMethod().IntentFun(this@DashboardActivity,RazorpayDemoActivity::class.java)
        }

    }

    private fun doDeleteRetrofit() {
        //TODO("Not yet implemented")
        var call : Call<GetSignupData> = apiInterface.deleteProfileData(
            sp.getString(ConstantSp.USERID,"")
        )

        call.enqueue(object : Callback<GetSignupData> {
            override fun onResponse(call: Call<GetSignupData>, response: Response<GetSignupData>) {
                //TODO("Not yet implemented")
                pd.dismiss()
                if(response.code() == 200){
                    if(response.body()?.status!!){
                        CommonMethod().ToasFunction(this@DashboardActivity, response.body()!!.message!!)
                        sp.edit().clear().commit()
                        CommonMethod().IntentFun(this@DashboardActivity,MainActivity::class.java)
                    }
                    else{
                        CommonMethod().ToasFunction(this@DashboardActivity, response.body()!!.message!!)
                    }
                }
                else{
                    CommonMethod().ToasFunction(this@DashboardActivity,"Server Error Code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GetSignupData>, t: Throwable) {
                //TODO("Not yet implemented")
                pd.dismiss()
                CommonMethod().ToasFunction(this@DashboardActivity,t.message.toString())
            }

        })
    }

    class doDelete(
        var context: Context,
        var sp: SharedPreferences
    ): AsyncTask<String, String, String>() {

        lateinit var pd : ProgressDialog

        override fun onPreExecute() {
            super.onPreExecute()
            pd = ProgressDialog(context)
            pd.setTitle("Please Wait...")
            pd.setCancelable(false)
            pd.show()
        }

        override fun doInBackground(vararg p0: String?): String {
            //TODO("Not yet implemented")
            var hashMap : HashMap<String,String> = HashMap()
            hashMap.put("userId", sp.getString(ConstantSp.USERID,"").toString())
            return MakeServiceCall().makeServiceCall(ConstantSp.BASE_URL+"deleteProfile.php",
                MakeServiceCall.POST,hashMap)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            pd.dismiss()
            var jsonObject : JSONObject = JSONObject(result)
            if(jsonObject.getBoolean("Status")){
                CommonMethod().ToasFunction(context,jsonObject.getString("Message"))
                sp.edit().clear().commit()
                CommonMethod().IntentFun(context,MainActivity::class.java)
            }
            else{
                CommonMethod().ToasFunction(context,jsonObject.getString("Message"))
            }
        }

    }

}