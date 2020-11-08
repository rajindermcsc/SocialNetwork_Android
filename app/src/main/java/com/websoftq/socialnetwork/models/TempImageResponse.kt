package com.websoftq.socialnetwork.models


import com.google.gson.annotations.SerializedName

data class TempImageResponse(
    @SerializedName("status_code")
    val statusCode: String,
    @SerializedName("status_message")
    val statusMessage: String,
    @SerializedName("temporary_image")
    val temporaryImage: String,
    @SerializedName("temporary_image_duration")
    val temporaryImageDuration: String
)