package internship.offlinekt

import android.app.ProgressDialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class CategoryActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var add: FloatingActionButton

    lateinit var arrayList: ArrayList<CategoryList>

    lateinit var apiInterface: ApiInterface
    lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        apiInterface = ApiClient().getClient()!!.create(ApiInterface::class.java)

        add = findViewById(R.id.category_add)
        add.setOnClickListener {
            CommonMethod().IntentFun(this@CategoryActivity, AddCategoryActivity::class.java)
        }

        recyclerView = findViewById(R.id.category_recyclerview)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = DefaultItemAnimator()

    }

    override fun onResume() {
        super.onResume()
        pd = ProgressDialog(this@CategoryActivity)
        pd.setMessage("Please Wait...")
        pd.setCancelable(false)
        pd.show()
        getData()
    }

    private fun getData() {
        //TODO("Not yet implemented")
        var call: Call<GetCategoryData> = apiInterface.getCategoryData()
        call.enqueue(object : Callback<GetCategoryData> {
            override fun onResponse(
                call: Call<GetCategoryData>,
                response: Response<GetCategoryData>
            ) {
                //TODO("Not yet implemented")
                pd.dismiss()
                if (response.code() == 200) {
                    if (response.body()?.status == true) {
                        arrayList = ArrayList()
                        for(i in 0..response.body()!!.categoryData!!.size-1){
                            var list : CategoryList = CategoryList(
                                response.body()!!.categoryData!!.get(i).categoryId!!,
                                response.body()!!.categoryData!!.get(i).name!!,
                                response.body()!!.categoryData!!.get(i).image!!,
                                )
                            arrayList.add(list)
                        }
                        var recycleAdapter : CategoryAdapter = CategoryAdapter(this@CategoryActivity,arrayList)
                        recyclerView.adapter = recycleAdapter
                    } else {
                        CommonMethod().ToasFunction(
                            this@CategoryActivity,
                            response.body()?.message!!
                        )
                    }
                } else {
                    CommonMethod().ToasFunction(
                        this@CategoryActivity,
                        "Server Error Code : ${response.code()}"
                    )
                }
            }

            override fun onFailure(call: Call<GetCategoryData>, t: Throwable) {
                //TODO("Not yet implemented")
                pd.dismiss()
                CommonMethod().ToasFunction(this@CategoryActivity, t.message.toString())
            }
        })
    }

}