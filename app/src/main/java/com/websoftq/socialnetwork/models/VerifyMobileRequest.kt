package com.websoftq.socialnetwork.models


import com.google.gson.annotations.SerializedName

data class VerifyMobileRequest(
    @SerializedName("Authkey")
    val authkey: String,
    @SerializedName("mobile")
    val mobile: String
)