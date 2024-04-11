package internship.offlinekt

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerviewDemoActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView

    val nameArray = arrayOf("Category 1", "Category 2", "Category 3", "Category 4", "Category 5")
    val offerArray = arrayOf("50 - 60% OFF", "50 - 80% OFF", "30 - 50% OFF", "30 - 40% OFF", "20 - 30% OFF")
    val imageArray = arrayOf(R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4, R.drawable.cat5)

    lateinit var arrayList : ArrayList<MyntraList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview_demo)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this@RecyclerviewDemoActivity)

        arrayList = ArrayList()
        for(i in 0..nameArray.size-1){
            var list : MyntraList = MyntraList(nameArray[i],offerArray[i],imageArray[i])
            arrayList.add(list)
        }
        var recycleAdapter : RecycleAdapter = RecycleAdapter(this@RecyclerviewDemoActivity,arrayList)
        recyclerView.adapter = recycleAdapter
    }
}