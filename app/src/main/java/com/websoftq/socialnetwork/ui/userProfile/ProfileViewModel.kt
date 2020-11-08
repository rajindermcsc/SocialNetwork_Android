package com.websoftq.socialnetwork.ui.userProfile

import Auth_Key
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.websoftq.meetfriends.projectView.retrofit.RestClient
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.models.MyPhotosResponse
import com.websoftq.socialnetwork.models.UserDetailResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ProfileViewModel (application: Application) : AndroidViewModel(application){

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorResponse: MutableLiveData<String> = MutableLiveData()
    var getResponse: MutableLiveData<MyPhotosResponse> = MutableLiveData()

    private var mContext: Context = application


    fun getMyPhotos(acessKey:String?){
        isLoading.value = true

        RestClient.getClient().getMyPhotos(Authkey = Auth_Key,Accesskey = acessKey).enqueue(object :
            Callback<MyPhotosResponse> {
            override fun onFailure(call: Call<MyPhotosResponse>, t: Throwable) {
                isLoading.value = false
                if (t is IOException) {
                    errorResponse.value = mContext.resources.getString(R.string.check_internet_connection)
                } else {
                    errorResponse.value = t.message
                }
            }

            override fun onResponse(
                call: Call<MyPhotosResponse>,
                response: Response<MyPhotosResponse>
            ) {
                isLoading.value = false
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



}