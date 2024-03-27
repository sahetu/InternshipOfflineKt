package internship.offlinekt

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DashboardActivity : AppCompatActivity() {

    lateinit var email : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        email = findViewById(R.id.dashboard_email)

        var bundle : Bundle = intent.extras!!
        //var sEmail : String = bundle.getString("EMAIL")!!
        val sEmail = bundle.getString("EMAIL")
        email.text = sEmail
    }
}