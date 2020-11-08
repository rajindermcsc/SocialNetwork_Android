package com.websoftq.socialnetwork.ui.community

import Auth_Key
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.websoftq.meetfriends.projectView.retrofit.RestClient
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.models.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class CommunityViewModel (application: Application) : AndroidViewModel(application){

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorResponse: MutableLiveData<String> = MutableLiveData()
    var getResponse: MutableLiveData<RandomUserResponse> = MutableLiveData()
    var getLogoutResponse: MutableLiveData<LogoutResponse> = MutableLiveData()
    var getNewMemberResponse: MutableLiveData<NewMemberResponse> = MutableLiveData()
    var getTempImageResponse: MutableLiveData<TempImageResponse> = MutableLiveData()

    private var mContext: Context = application


    fun getRandomUsers(){
        isLoading.value = true

        RestClient.getClient().getRandomUsers( Auth_Key, "0").enqueue(object :
            Callback<RandomUserResponse> {
            override fun onFailure(call: Call<RandomUserResponse>, t: Throwable) {
                isLoading.value = false
                if (t is IOException) {
                    errorResponse.value = mContext.resources.getString(R.string.check_internet_connection)
                } else {
                    errorResponse.value = t.message
                }
            }

            override fun onResponse(
                call: Call<RandomUserResponse>,
                response: Response<RandomUserResponse>
            ) {

                try {
                    if (response.body() != null) {
                        response.body()?.let {
                            if (it.statusCode =="200"){
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


    fun getNewMembers(){
        isLoading.value = true

        RestClient.getClient().getNewMemebers( Auth_Key, "0").enqueue(object :
            Callback<NewMemberResponse> {
            override fun onFailure(call: Call<NewMemberResponse>, t: Throwable) {
                isLoading.value = false
                if (t is IOException) {
                    errorResponse.value = mContext.resources.getString(R.string.check_internet_connection)
                } else {
                    errorResponse.value = t.message
                }
            }

            override fun onResponse(
                call: Call<NewMemberResponse>,
                response: Response<NewMemberResponse>
            ) {
                isLoading.value = false
                try {
                    if (response.body() != null) {
                        response.body()?.let {
                            if (it.statusCode =="200"){
                                getNewMemberResponse.value = it
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



    fun tempImageResponse(accessKey: String?, duration :String, userImage:String){
        isLoading.value = true

        RestClient.getClient().addTempImage( Auth_Key,accessKey, duration,userImage ).enqueue(object :
            Callback<TempImageResponse> {
            override fun onFailure(call: Call<TempImageResponse>, t: Throwable) {
                isLoading.value = false
                if (t is IOException) {
                    errorResponse.value = mContext.resources.getString(R.string.check_internet_connection)
                } else {
                    errorResponse.value = t.message
                }
            }

            override fun onResponse(
                call: Call<TempImageResponse>,
                response: Response<TempImageResponse>
            ) {
                isLoading.value = false
                try {
                    if (response.body() != null) {
                        response.body()?.let {
                            if (it.statusCode =="200"){
                                getTempImageResponse.value = it
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


    fun getLogout(accessKey:String?){
        isLoading.value = true

        RestClient.getClient().logout( Auth_Key, accessKey).enqueue(object :
            Callback<LogoutResponse> {
            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                isLoading.value = false
                if (t is IOException) {
                    errorResponse.value = mContext.resources.getString(R.string.check_internet_connection)
                } else {
                    errorResponse.value = t.message
                }
            }

            override fun onResponse(
                call: Call<LogoutResponse>,
                response: Response<LogoutResponse>
            ) {
                isLoading.value = false

                try {
                    if (response.body() != null) {
                        response.body()?.let {
                            if (it.statusCode =="200"){
                                getLogoutResponse.value = it
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