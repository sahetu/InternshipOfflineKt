package internship.offlinekt.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.text.TextUtils
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import internship.offlinekt.ConstantSp
import internship.offlinekt.NotificationActivity
import internship.offlinekt.R
import org.json.JSONException
import org.json.JSONObject

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = MyFirebaseMessagingService::class.java.simpleName

    private var notificationUtils: NotificationUtils? = null
    private var count = 0

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.e("newToken", s)
        getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE).edit().putString(ConstantSp.FCM_ID, s)
            .apply()
    }

    fun getToken(context: Context): String? {
        return context.getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE)
            .getString(ConstantSp.FCM_ID, "")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //Calling method to generate notification
        Log.e("RUNRUN", remoteMessage.toString() + "")
        Log.e("RUNRUN", "From: " + remoteMessage.from)
        Log.e("RUNRUN", "Message data payload: " + remoteMessage.getData())
        val title = remoteMessage.getNotification()!!.title
        val message = remoteMessage.getNotification()!!.body
        val click_action = remoteMessage.getNotification()!!.clickAction
        val intent = Intent(click_action)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val contentIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationBuilder = NotificationCompat.Builder(this)
        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(message)
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setContentIntent(contentIntent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

    //This method is only generating push notification
    private fun sendNotification(messageTitle: String, messageBody: String) {
        val contentIntent: PendingIntent? = null
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(contentIntent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(count, notificationBuilder.build())
        count++
    }

    private fun handleNotification(message: String) {
        if (!NotificationUtils.isAppIsInBackground(applicationContext)) {
            // app is in foreground, broadcast the push message
            val sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE)
            val pushNotification = Intent(Config.PUSH_NOTIFICATION)
            pushNotification.putExtra("message", message)
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)

            // play notification sound
            val notificationUtils = NotificationUtils(applicationContext)
            notificationUtils.playNotificationSound()
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }

    private fun handleDataMessage(json: JSONObject) {
        Log.e(TAG, "push json: $json")
        try {
            val data = json.getJSONObject("data")
            val title = data.getString("title")
            val message = data.getString("message")
            val isBackground = data.getBoolean("is_background")
            val imageUrl = data.getString("image")
            val timestamp = data.getString("timestamp")
            val payload = data.getJSONObject("payload")
            Log.e(TAG, "title: $title")
            Log.e(TAG, "message: $message")
            Log.e(TAG, "isBackground: $isBackground")
            Log.e(TAG, "payload: $payload")
            Log.e(TAG, "imageUrl: $imageUrl")
            Log.e(TAG, "timestamp: $timestamp")
            if (!NotificationUtils.isAppIsInBackground(applicationContext)) {
                // app is in foreground, broadcast the push message
                val sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE)
                val pushNotification = Intent(Config.PUSH_NOTIFICATION)
                pushNotification.putExtra("message", message)
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)
                // play notification sound
                val notificationUtils = NotificationUtils(applicationContext)
                notificationUtils.playNotificationSound()
                val resultIntent = Intent(
                    applicationContext,
                    NotificationActivity::class.java
                )
                resultIntent.putExtra("message", message)
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(
                        applicationContext,
                        title,
                        message,
                        timestamp,
                        resultIntent
                    )
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(
                        applicationContext,
                        title,
                        message,
                        timestamp,
                        resultIntent,
                        imageUrl
                    )
                }
            } else {
                // app is in background, show the notification in notification tray
                val sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE)
                val resultIntent = Intent(
                    applicationContext,
                    NotificationActivity::class.java
                )
                resultIntent.putExtra("message", message)
                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(
                        applicationContext,
                        title,
                        message,
                        timestamp,
                        resultIntent
                    )
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(
                        applicationContext,
                        title,
                        message,
                        timestamp,
                        resultIntent,
                        imageUrl
                    )
                }
            }
        } catch (e: JSONException) {
            Log.e(TAG, "Json Exception: " + e.message)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: " + e.message)
        }
    }

    /**
     * Showing notification with text only
     */
    private fun showNotificationMessage(
        context: Context,
        title: String,
        message: String,
        timeStamp: String,
        intent: Intent
    ) {
        notificationUtils = NotificationUtils(context)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        notificationUtils!!.showNotificationMessage(title, message, timeStamp, intent)
    }

    /**
     * Showing notification with text and image
     */
    private fun showNotificationMessageWithBigImage(
        context: Context,
        title: String,
        message: String,
        timeStamp: String,
        intent: Intent,
        imageUrl: String
    ) {
        notificationUtils = NotificationUtils(context)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        notificationUtils!!.showNotificationMessage(title, message, timeStamp, intent, imageUrl)
    }

}