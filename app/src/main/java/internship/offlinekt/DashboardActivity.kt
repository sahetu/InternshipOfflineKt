package internship.offlinekt

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        email = findViewById(R.id.dashboard_email)

        var bundle: Bundle = intent.extras!!
        //var sEmail : String = bundle.getString("EMAIL")!!
        val sEmail = bundle.getString("EMAIL")
        email.text = sEmail

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

    }
}