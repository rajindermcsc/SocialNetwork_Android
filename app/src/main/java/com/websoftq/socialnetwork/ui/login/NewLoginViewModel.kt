package com.websoftq.socialnetwork.ui.login

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.websoftq.meetfriends.projectView.retrofit.RestClient
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.models.LoginRequest
import com.websoftq.socialnetwork.models.LoginResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class NewLoginViewModel(application: Application) : AndroidViewModel(application) {


    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorResponse: MutableLiveData<String> = MutableLiveData()
    var getResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    private var mContext: Context = application

    fun setLoging(authKey: String, password: String, username: String, platform :String) {
        isLoading.value = true

        RestClient.getClient().getLogin(authKey, password, username, platform).enqueue(object :
            Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                isLoading.value = false
                if (t is IOException) {
                    errorResponse.value =
                        mContext.resources.getString(R.string.check_internet_connection)
                } else {
                    errorResponse.value = t.message
                }
            }
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                isLoading.value = false
                try {
                    if (response.body() != null) {
                        response.body()?.let {
                            if (it.statusCode == "200"){
                                getResponse.value = it
                            }else{
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
    }


}