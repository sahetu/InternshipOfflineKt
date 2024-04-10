package internship.offlinekt

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserSimpleListActivity : AppCompatActivity() {

    lateinit var simpleList : ListView

    val cityArray = arrayOf("Ahmedabad","Rajkot","Vadodara","Surat","Gandhinagar")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_simple_list)

        simpleList = findViewById(R.id.user_simple_list)

        var arrayAdapter : ArrayAdapter<*> = ArrayAdapter(this@UserSimpleListActivity,android.R.layout.simple_list_item_1,cityArray)
        simpleList.adapter = arrayAdapter

        simpleList.onItemClickListener = object : OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //TODO("Not yet implemented")
                CommonMethod().ToasFunction(this@UserSimpleListActivity,cityArray[p2])
            }
        }

    }
}