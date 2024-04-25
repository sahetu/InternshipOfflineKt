package internship.offlinekt

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {

    @FormUrlEncoded
    @POST("signup.php")
    fun getSignupData(
        @Field("username") username: String?,
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("contact") contact: String?,
        @Field("password") password: String?,
        @Field("gender") gender: String?,
        @Field("city") city: String?
    ): Call<GetSignupData>

    @FormUrlEncoded
    @POST("login.php")
    fun getLoginData(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<GetLoginData>

    @FormUrlEncoded
    @POST("updateProfile.php")
    fun updateProfileData(
        @Field("username") username: String?,
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("contact") contact: String?,
        @Field("password") password: String?,
        @Field("gender") gender: String?,
        @Field("city") city: String?,
        @Field("userId") userId: String?
    ): Call<GetSignupData>

    @FormUrlEncoded
    @POST("deleteProfile.php")
    fun deleteProfileData(
        @Field("userId") userId: String?
    ): Call<GetSignupData>

    @Multipart
    @POST("addCategory.php")
    fun addCategoryData(
        @Part("name") user_longitude: RequestBody?,
        @Part imagePassport: MultipartBody.Part?
    ): Call<GetSignupData?>?

    @GET("getCategory.php")
    fun getCategoryData(): Call<GetCategoryData?>?

    @FormUrlEncoded
    @POST("update_fcm.php")
    fun updateFcmData(
        @Field("fcm_token") fcm_token: String?,
        @Field("userId") userId: String?
    ): Call<GetSignupData?>?

    @FormUrlEncoded
    @POST("getNotification.php")
    fun getNotificationData(
        @Field("userId") userId: String?
    ): Call<GetNotificationData?>?

    @FormUrlEncoded
    @POST("send_notification.php")
    fun sendNotificationData(
        @Field("message") message: String?
    ): Call<GetSignupData?>?

}