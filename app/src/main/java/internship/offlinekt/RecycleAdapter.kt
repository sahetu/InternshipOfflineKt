package internship.offlinekt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapter(var context: Context,var arrayList: ArrayList<MyntraList>) : RecyclerView.Adapter<RecycleAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        //TODO("Not yet implemented")
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.custom_user_card,parent,false)
        return MyHolder(view)
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name : TextView = itemView.findViewById(R.id.custom_user_card_name)
        var offer : TextView = itemView.findViewById(R.id.custom_user_card_offer)
        var image : ImageView = itemView.findViewById(R.id.custom_user_card_image)

    }

    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        //TODO("Not yet implemented")
        holder.name.text = arrayList.get(position).name
        holder.offer.text = arrayList.get(position).offer
        holder.image.setImageResource(arrayList.get(position).image)

        holder.itemView.setOnClickListener {
            CommonMethod().ToasFunction(context,arrayList.get(position).name)
        }

    }

}
