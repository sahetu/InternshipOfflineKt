package internship.offlinekt

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendNotificationActivity : AppCompatActivity() {

    lateinit var message: EditText
    lateinit var submit: Button

    lateinit var apiInterface: ApiInterface
    lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_notification)
        apiInterface = ApiClient().getClient()!!.create(ApiInterface::class.java)

        message = findViewById(R.id.send_notification_message)
        submit = findViewById(R.id.send_notification_submit)

        submit.setOnClickListener {
            if (message.text.toString().trim().equals("")) {
                message.error = "Message Required"
            } else {
                pd = ProgressDialog(this@SendNotificationActivity)
                pd.setMessage("Please Wait...")
                pd.setCancelable(false)
                pd.show()
                addNotification()
            }
        }

    }

    private fun addNotification() {
        val call = apiInterface.sendNotificationData(message.text.toString())
        call!!.enqueue(object : Callback<GetSignupData> {
            override fun onResponse(call: Call<GetSignupData>, response: Response<GetSignupData>) {
                pd.dismiss()
                if (response.code() == 200) {
                    if (response.body()!!.status!!) {
                        CommonMethod().ToasFunction(this@SendNotificationActivity,
                            response.body()!!.message!!
                        )
                        onBackPressed()
                    } else {
                        CommonMethod().ToasFunction(this@SendNotificationActivity, response.body()!!.message!!)
                    }
                } else {
                    CommonMethod().ToasFunction(
                        this@SendNotificationActivity,
                        "Server Error Code : " + response.code()
                    )
                }
            }

            override fun onFailure(call: Call<GetSignupData>, t: Throwable) {
                pd.dismiss()
                CommonMethod().ToasFunction(this@SendNotificationActivity, t.message.toString())
            }
        })
    }

}