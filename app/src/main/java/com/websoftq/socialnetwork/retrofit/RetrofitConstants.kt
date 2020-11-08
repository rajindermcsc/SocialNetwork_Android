
import okhttp3.RequestBody

const val BASE_URL = "https://websoftquality.com/sns/api/"

fun createPartFromString(partString: String): RequestBody {
    return RequestBody.create(okhttp3.MultipartBody.FORM, partString)
}
const val REPORT_POST = "Post/ReportPost"
const val SAVE_POST = "Post/SavePost"
const val LIKE_POST = "Post/PostLike"
const val GET_POST= "user/GetUserPost"
const val GET_LOGIN = "login"
const val VERIFY_MOBILE = "verify_mobile"
const val Sign_UP = "sign_up"
const val Current_Topic = "current_topics"
const val Random_Users = "random_users"
const val New_Members = "new_members"
const val User_Profile = "user_profile"
const val logout = "logout"
const val Check_User_Name = "check_valid_username"
const val My_Photos = "my_photos"
const val Add_Temp_Image = "add_temporary_image"
const val add_topics = "add_topics"

const val TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRxYUBnbWFpbC5jb20iLCJ1bmlxdWVfbmFtZSI6ImEiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9zaWQiOiIxMDE0IiwibmJmIjoxNTk4MjAxNzY5LCJleHAiOjE1OTgyMDM1NjksImlhdCI6MTU5ODIwMTc2OX0.aNOqhLTxNDBJaq15rNYzLMIBI2pldWZTG-gGQnw4w4U"
//const val SUBMIT_PROFILE = "User/UpdateUserProfiles"
const val SUBMIT_PROFILE = "User/UpdateUserProfileTest"
const val GET_FRIEND_LIST_SUGGESTION = "Post/GetFriendSuggesstion"
const val SEND_REQUEST = "Post/SaveUserFriendRequest"
const val SUBMIT_POST = "Post/CreatePost"
const val UPDATE_POST = "Post/UpdatePost"
const val UNLIKE_POST = "Post/PostDisLike"
const val GET_USER_POTS = "Post/GetUserPostList"
const val DELETE_POST = "Post/DeletePost"
const val GET_USER_PROFILE = "User/GetUserProfile"
const val FORGOT_PASSWORD = "User/ForgotPassword"
const val POST_COMMENT = "Post/PostComment"
const val Auth_Key = "h00SG6dT3CqxrMYjHnw5mSmPOZ1psRlj"
const val VIDEO_URL = "https://websoftquality.com/sns/assets/topicVideos/"
const val IMAGE_URL = "https://websoftquality.com/sns/assets/img/studentImages/"

