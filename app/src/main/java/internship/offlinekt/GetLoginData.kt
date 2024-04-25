package internship.offlinekt

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetLoginData {

    @SerializedName("Status")
    @Expose
    var status: Boolean? = null

    @SerializedName("Message")
    @Expose
    var message: String? = null

    @SerializedName("UserDetails")
    @Expose
    var userDetails: List<GetUserDetail>? = null


    class GetUserDetail {
        @SerializedName("userId")
        @Expose
        var userId: String? = null

        @SerializedName("userName")
        @Expose
        var userName: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("contact")
        @Expose
        var contact: String? = null

        @SerializedName("gender")
        @Expose
        var gender: String? = null

        @SerializedName("city")
        @Expose
        var city: String? = null
    }


}
