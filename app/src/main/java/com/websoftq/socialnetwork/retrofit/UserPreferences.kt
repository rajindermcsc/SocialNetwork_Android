
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

private const val PREFS_FILE_NAME = "MeetFriends"
private const val PREFS_IS_LOGIN = "isLogin"
private const val PREFS_IS_REMEMBER = "isRemember"
private const val PREFS_DEVICE_CONFIG = "device data"
private const val  PREFS_IS_PROFILE_SUBMIT = "isProfileSubmitted"
private const val  ACCESS_KEY = "ACCESS_KEY"
private const val  USER_NAME = "USER_NAME"
private const val  DESCRIPTION = "DESCRIPTION"
private const val  USER_IMAGE = "USER_IMAGE"

class UserPreferences (context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    private val mEditor: SharedPreferences.Editor = sharedPreferences.edit()


    fun clearPrefs() {
        mEditor.apply {
            clear()
            commit()
        }
    }

    fun isAlreadyLogin(): Boolean {
        return sharedPreferences.getBoolean(PREFS_IS_LOGIN, false)
    }

    fun isFinishedProfile(): Boolean {
        return sharedPreferences.getBoolean(PREFS_IS_PROFILE_SUBMIT, false)
    }

    fun setLogin(b: Boolean) {
        mEditor.putBoolean(PREFS_IS_LOGIN, b)
        mEditor.apply()
    }

    fun setAccessKey(b: String) {
        mEditor.putString(ACCESS_KEY, b)
        mEditor.apply()
    }

    fun getAccessKey():String? {
       return sharedPreferences.getString(ACCESS_KEY, "")
    }


       fun setUserName(b: String) {
        mEditor.putString(USER_NAME, b)
        mEditor.apply()
    }

    fun getUserName():String? {
        return sharedPreferences.getString(USER_NAME, "")
    }


    fun setUserImage(b: String) {
        mEditor.putString(USER_IMAGE, b)
        mEditor.apply()
    }

    fun getUserImage():String? {
        return sharedPreferences.getString(USER_IMAGE, "")
    }

    fun setUserDescription(b: String) {
        mEditor.putString(DESCRIPTION, b)
        mEditor.apply()
    }

    fun getUserDescription():String? {
        return sharedPreferences.getString(DESCRIPTION, "")
    }


    fun setRemeber(s: Boolean) {
        mEditor.putBoolean(PREFS_IS_REMEMBER, s)
        mEditor.apply()
    }

   /* fun setLoginData(data: DataLogin) {
        mEditor.putString(PREFS_DEVICE_CONFIG, Gson().toJson(data))
        mEditor.apply()
    }

    fun getDeviceData(): DataLogin? {
        return Gson().fromJson(sharedPreferences.getString(PREFS_DEVICE_CONFIG, ""), DataLogin::class.java)
    }
*/
    fun setProfileUpdate(b: Boolean) {
        mEditor.putBoolean(PREFS_IS_PROFILE_SUBMIT, b)
        mEditor.apply()
    }
}