package internship.offlinekt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NotificationAdapter(var context: Context, var arrayList: ArrayList<NotificationList>) : RecyclerView.Adapter<NotificationAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        //TODO("Not yet implemented")
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.custom_notification,parent,false)
        return MyHolder(view)
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var message : TextView = itemView.findViewById(R.id.custom_notification_message)
        var date : TextView = itemView.findViewById(R.id.custom_notification_date)
    }

    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        //TODO("Not yet implemented")
        holder.message.text = arrayList.get(position).message
        holder.date.text = arrayList.get(position).date

    }

}

