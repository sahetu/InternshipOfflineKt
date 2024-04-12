package internship.offlinekt

import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
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

    lateinit var sp : SharedPreferences

    lateinit var db : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

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
            var deleteQuery : String = "DELETE FROM USERS WHERE USERID='"+sp.getString(ConstantSp.USERID,"")+"'"
            db.execSQL(deleteQuery)
            sp.edit().clear().commit()
            CommonMethod().IntentFun(this@DashboardActivity,MainActivity::class.java)
            finish()
        }

        profile = findViewById(R.id.dashboard_profile)
        profile.setOnClickListener {
            CommonMethod().IntentFun(this@DashboardActivity,ProfileActivity::class.java)
        }

    }
}