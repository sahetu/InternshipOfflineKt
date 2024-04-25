package internship.offlinekt

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import android.view.View
import android.view.Window
import android.widget.Button


class ConnectionDetector(private val _context: Context) {
    fun networkConnected(): Boolean {
        val connectivity = _context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null) for (i in info.indices) if (info[i].state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }

    fun networkDisconnected(): Boolean {
        return if (networkConnected()) {
            true
        } else {
            val dialog = Dialog(_context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.connection_checker)
            val retry = dialog.findViewById<View>(R.id.dialog_ok) as Button
            val connect = dialog.findViewById<View>(R.id.dialog_cancel) as Button
            dialog.show()
            dialog.setCancelable(false)
            retry.setOnClickListener {
                dialog.dismiss()
                networkConnected()
            }
            connect.setOnClickListener {
                dialog.dismiss()
                _context.startActivity(Intent(Settings.ACTION_SETTINGS))
            }
            false
        }
    }
}