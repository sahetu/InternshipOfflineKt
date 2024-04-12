package internship.offlinekt

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {

    lateinit var sp : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE)

        Handler().postDelayed(Runnable {
            if(sp.getString(ConstantSp.USERID,"").equals("")){
                CommonMethod().IntentFun(this@SplashActivity,MainActivity::class.java)
                finish()
            }
            else{
                CommonMethod().IntentFun(this@SplashActivity,DashboardActivity::class.java)
                finish()
            }
        },1000)

    }
}