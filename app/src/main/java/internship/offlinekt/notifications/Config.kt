package internship.offlinekt.notifications

class Config {

    companion object{
        // broadcast receiver intent filters
        val REGISTRATION_COMPLETE = "registrationComplete"
        val PUSH_NOTIFICATION = "pushNotification"

        // id to handle the notification in the notification tray
        val NOTIFICATION_ID = 100
        val NOTIFICATION_ID_BIG_IMAGE = 101

        val SHARED_PREF = "ah_firebase"
    }

}