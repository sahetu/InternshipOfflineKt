package internship.offlinekt

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var add: FloatingActionButton

    lateinit var apiInterface: ApiInterface
    lateinit var pd: ProgressDialog

    lateinit var arrayList: ArrayList<NotificationList>
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE)

        apiInterface = ApiClient().getClient()!!.create(ApiInterface::class.java)

        add = findViewById(R.id.notification_add)

        add.setOnClickListener {
            CommonMethod().IntentFun(this@NotificationActivity,SendNotificationActivity::class.java)
        }

        recyclerView = findViewById(R.id.notification_recyclerview)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = DefaultItemAnimator()

    }

    override fun onResume() {
        super.onResume()
        pd = ProgressDialog(this@NotificationActivity)
        pd.setMessage("Please Wait...")
        pd.setCancelable(false)
        pd.show()
        getData()
    }

    private fun getData() {
        //TODO("Not yet implemented")
        var call: Call<GetNotificationData> = apiInterface.getNotificationData(sp.getString(ConstantSp.USERID,""))
        call.enqueue(object : Callback<GetNotificationData> {
            override fun onResponse(
                call: Call<GetNotificationData>,
                response: Response<GetNotificationData>
            ) {
                //TODO("Not yet implemented")
                pd.dismiss()
                if (response.code() == 200) {
                    if (response.body()?.status == true) {
                        arrayList = ArrayList()
                        for(i in 0..response.body()!!.notificationData!!.size-1){
                            var list : NotificationList = NotificationList(
                                response.body()!!.notificationData!!.get(i).id!!,
                                response.body()!!.notificationData!!.get(i).message!!,
                                response.body()!!.notificationData!!.get(i).createdDate!!,
                            )
                            arrayList.add(list)
                        }
                        var recycleAdapter : NotificationAdapter = NotificationAdapter(this@NotificationActivity,arrayList)
                        recyclerView.adapter = recycleAdapter
                    } else {
                        CommonMethod().ToasFunction(
                            this@NotificationActivity,
                            response.body()?.message!!
                        )
                    }
                } else {
                    CommonMethod().ToasFunction(
                        this@NotificationActivity,
                        "Server Error Code : ${response.code()}"
                    )
                }
            }

            override fun onFailure(call: Call<GetNotificationData>, t: Throwable) {
                //TODO("Not yet implemented")
                pd.dismiss()
                CommonMethod().ToasFunction(this@NotificationActivity, t.message.toString())
            }
        })
    }

}