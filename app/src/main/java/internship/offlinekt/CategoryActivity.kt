package internship.offlinekt

import android.app.ProgressDialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.create

class CategoryActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var add : FloatingActionButton

    lateinit var arrayList : ArrayList<CategoryList>

    lateinit var apiInterface: ApiInterface
    lateinit var pd : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        apiInterface = ApiClient().getClient()!!.create(ApiInterface::class.java)

    }
}