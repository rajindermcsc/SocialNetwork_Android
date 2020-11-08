import com.websoftq.socialnetwork.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList
import java.util.HashMap


interface API {

    @FormUrlEncoded
    @POST(GET_LOGIN)
    fun getLogin(
        @Field("Authkey") Authkey: String, @Field("password") password: String,
        @Field("username") username: String, @Field("platform") platform: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST(VERIFY_MOBILE)
    fun verifyMobile(
        @Field("Authkey") Authkey: String,
        @Field("mobile") password: String
    ): Call<VerifyMobileResponse>

    @Multipart
    @POST(Sign_UP)
    fun signUp(
        @Part("mobile") mobile: RequestBody, @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody, @Part("dob") dob: RequestBody,
        @Part("school") school: RequestBody, @Part("school_coordinates") school_coordinates: RequestBody,
        @Part("password") password: RequestBody, @Part("description") description: RequestBody,
        @Part("Authkey") Authkey: RequestBody, @PartMap() userimage:Map<String,RequestBody>,
        @Part("platform") platform: RequestBody, @Part("device_id") device_id: RequestBody,
        @Part("username") username: RequestBody
    ): Call<SignUpResponse>


    @FormUrlEncoded
    @POST(Current_Topic)
    fun getCurrentTopic(@Field("Authkey") Authkey: String): Call<CurrentTopicResponse>

    @FormUrlEncoded
    @POST(Random_Users)
    fun getRandomUsers(
        @Field("Authkey") Authkey: String,
        @Field("page_no") page_no: String
    ): Call<RandomUserResponse>

    @FormUrlEncoded
    @POST(New_Members)
    fun getNewMemebers(
        @Field("Authkey") Authkey: String,
        @Field("page_no") page_no: String
    ): Call<NewMemberResponse>

    @FormUrlEncoded
    @POST(User_Profile)
    fun getUserDetail(
        @Field("Authkey") Authkey: String,
        @Field("user_id") user_id: Int
    ): Call<UserDetailResponse>

    @FormUrlEncoded
    @POST(logout)
    fun logout(
        @Field("Authkey") Authkey: String,
        @Field("Accesskey") Accesskey: String?
    ): Call<LogoutResponse>


    @FormUrlEncoded
    @POST(Check_User_Name)
    fun checkUserName(
        @Field("Authkey") Authkey: String,
        @Field("username") username: String
    ): Call<LogoutResponse>

    @FormUrlEncoded
    @POST(My_Photos)
    fun getMyPhotos(
        @Field("Authkey") Authkey: String,
        @Field("Accesskey") Accesskey: String?
    ): Call<MyPhotosResponse>


    @FormUrlEncoded
    @POST(Add_Temp_Image)
    fun addTempImage(
        @Field("Authkey") Authkey: String,
        @Field("Accesskey") Accesskey: String?,
        @Field("duration") duration: String,
        @Field("userimage[]") userimage: String
    ): Call<TempImageResponse>



    @FormUrlEncoded
    @POST(add_topics)
    fun addTopic(
        @Field("Authkey") Authkey: String,
        @Field("Accesskey") Accesskey: String?,
        @Field("topic_name") topic_name: String,
        @Field("topic_description") topic_description: String,
        @Field("topic_video") topic_video: String
    ): Call<AddTopicResponse>



}