package com.websoftq.socialnetwork.ui.verifyMobile

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.websoftq.meetfriends.projectView.retrofit.RestClient
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.models.ArrayListAge
import com.websoftq.socialnetwork.models.SignUpResponse
import com.websoftq.socialnetwork.models.VerifyMobileResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class VerificationCodeViewModel(application: Application) : AndroidViewModel(application) {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorResponse: MutableLiveData<String> = MutableLiveData()
    var getResponse: MutableLiveData<VerifyMobileResponse> = MutableLiveData()
    var getSignUpResponse: MutableLiveData<SignUpResponse> = MutableLiveData()
    private var mContext: Context = application

/*    fun signUP(
        mobileNo: :RequestBody,
        firstName: String,
        lastName: String,
        dob: String,
        school: String,
        schoolCordinates: String,
        password: String,
        description: String,
        authKey: String,
        userImage:Array<String>,
        platform: String,
        deviceId: String,
        userName: String
    ) {
        isLoading.value = true

        RestClient.getClient().signUp(mobileNo, firstName, lastName, dob, school, schoolCordinates, password, description, authKey,
        userImage, platform, deviceId, userName).enqueue(object :
            Callback<SignUpResponse> {
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                isLoading.value = false
                if (t is IOException) {
                    errorResponse.value =
                        mContext.resources.getString(R.string.check_internet_connection)
                } else {
                    errorResponse.value = t.message
                }
            }

            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                isLoading.value = false
                try {
                    if (response.body() != null) {
                        response.body()?.let {
                            if (it.statusCode == "200") {
                                getSignUpResponse.value = it
                            } else {
                                errorResponse.value = it.statusMessage
                            }
                        }
                    } else {
                        response.errorBody()?.let {
                            val jObjError = JSONObject(it.string())
                            errorResponse.value = jObjError.getString("message")
                        }
                    }
                } catch (e: Exception) {
                    errorResponse.value = e.toString()
                }
            }
        })
    }*/


}