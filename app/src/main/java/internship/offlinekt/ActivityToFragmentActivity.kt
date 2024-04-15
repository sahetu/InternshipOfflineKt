package internship.offlinekt

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class ActivityToFragmentActivity : AppCompatActivity() {

    lateinit var submit : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_fragment)

        submit = findViewById(R.id.open_fragment_button)

        submit.setOnClickListener {
            var fragmentManager : FragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.open_fragment_layout,DemoFragment()).addToBackStack("").commit()
        }

    }
}