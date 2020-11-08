package com.websoftq.socialnetwork.models


import com.google.gson.annotations.SerializedName

 class LoginRequest{
     @SerializedName("username")
     var username: String=""
     @SerializedName("password")
     var password: String=""
     @SerializedName("Authkey")
     var Authkey: String =""

 }

