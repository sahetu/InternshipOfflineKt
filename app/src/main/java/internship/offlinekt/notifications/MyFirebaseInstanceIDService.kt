package internship.offlinekt.notifications

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import internship.offlinekt.ConstantSp

class MyFirebaseInstanceIDService : FirebaseMessagingService() {

    private val TAG = MyFirebaseInstanceIDService::class.java.simpleName
    var sp: SharedPreferences? = null

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.d("NEW_TOKEN", s)
        storeRegIdInPref(s)

        // sending reg id to your server
        sendRegistrationToServer(s)

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        val registrationComplete = Intent(Config.REGISTRATION_COMPLETE)
        registrationComplete.putExtra("token", s)
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete)
    }

    private fun sendRegistrationToServer(token: String) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: $token")
    }

    private fun storeRegIdInPref(token: String) {
        sp = getApplicationContext().getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE)
        sp!!.edit().putString(ConstantSp.FCM_ID, token).commit()
        val pref: SharedPreferences =
            getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0)
        val editor = pref.edit()
        editor.putString("regId", token)
        editor.commit()
    }

}