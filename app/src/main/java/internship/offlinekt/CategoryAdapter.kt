package internship.offlinekt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CategoryAdapter(var context: Context,var arrayList: ArrayList<CategoryList>) : RecyclerView.Adapter<CategoryAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        //TODO("Not yet implemented")
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.custom_category,parent,false)
        return MyHolder(view)
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name : TextView = itemView.findViewById(R.id.custom_category_name)
        var image : ImageView = itemView.findViewById(R.id.custom_category_image)
    }

    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        //TODO("Not yet implemented")
        holder.name.text = arrayList.get(position).name
        //holder.image.setImageResource(arrayList.get(position).image)
        Glide.with(context).load(arrayList.get(position).image).placeholder(R.mipmap.ic_launcher).into(holder.image)

        holder.itemView.setOnClickListener {
            CommonMethod().ToasFunction(context,arrayList.get(position).name)
        }

    }

}
