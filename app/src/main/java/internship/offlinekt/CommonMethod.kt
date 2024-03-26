package internship.offlinekt

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class CommonMethod {

    fun ToasFunction(context : Context,message : String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    fun SnackBarFun(view : View,message : String){
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show()
    }

    fun IntentFun(context : Context,nextClass : Class<*>){
        var intent : Intent = Intent(context,nextClass)
        context.startActivity(intent)
    }

}