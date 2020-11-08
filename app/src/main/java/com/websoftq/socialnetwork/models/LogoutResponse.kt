package com.websoftq.socialnetwork.models


import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("status_code")
    val statusCode: String,
    @SerializedName("status_message")
    val statusMessage: String
)