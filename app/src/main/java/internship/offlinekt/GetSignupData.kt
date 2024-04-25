package internship.offlinekt

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetSignupData {

    @SerializedName("Status")
    @Expose
    var status: Boolean? = null

    @SerializedName("Message")
    @Expose
    var message: String? = null

}