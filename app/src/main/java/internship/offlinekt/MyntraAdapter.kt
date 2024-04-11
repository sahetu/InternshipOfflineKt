package internship.offlinekt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MyntraAdapter(var context: Context,var arrayList: ArrayList<MyntraList>) : BaseAdapter() {
    override fun getCount(): Int {
        //TODO("Not yet implemented")
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        //TODO("Not yet implemented")
        return arrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        //TODO("Not yet implemented")
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        //TODO("Not yet implemented")
        var layoutInflat: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View = layoutInflat.inflate(R.layout.custom_user, null)

        var image: ImageView = view.findViewById(R.id.custom_user_image)
        var name: TextView = view.findViewById(R.id.custom_user_name)
        var offer: TextView = view.findViewById(R.id.custom_user_offer)

        image.setImageResource(arrayList.get(p0).image)
        name.text = arrayList[p0].name
        offer.text = arrayList.get(p0).offer

        return view
    }
}
