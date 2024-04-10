package internship.offlinekt

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserCustomListActivity : AppCompatActivity() {

    lateinit var listView : ListView
    val nameArray = arrayOf("Category 1", "Category 2", "Category 3", "Category 4", "Category 5")
    val offerArray = arrayOf("50 - 60% OFF", "50 - 80% OFF", "30 - 50% OFF", "30 - 40% OFF", "20 - 30% OFF")
    val imageArray = arrayOf(R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4, R.drawable.cat5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_custom_list)

        listView = findViewById(R.id.user_custom_list)

        var custAdapter : CustomListAdapter = CustomListAdapter(this@UserCustomListActivity,nameArray,offerArray,imageArray)
        listView.adapter = custAdapter

    }
}