package internship.offlinekt

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetCategoryData {

    @SerializedName("Status")
    @Expose
    var status: Boolean? = null

    @SerializedName("Message")
    @Expose
    var message: String? = null

    @SerializedName("categoryData")
    @Expose
    var categoryData: List<GetCategoryResponse>? = null


    class GetCategoryResponse {
        @SerializedName("categoryId")
        @Expose
        var categoryId: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null
    }


}
