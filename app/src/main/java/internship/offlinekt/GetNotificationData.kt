package internship.offlinekt

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetNotificationData {

    @SerializedName("Status")
    @Expose
    var status: Boolean? = null

    @SerializedName("Message")
    @Expose
    var message: String? = null

    @SerializedName("notificationData")
    @Expose
    var notificationData: List<GetNotificationResponse>? = null


    class GetNotificationResponse {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("created_date")
        @Expose
        var createdDate: String? = null
    }


}
