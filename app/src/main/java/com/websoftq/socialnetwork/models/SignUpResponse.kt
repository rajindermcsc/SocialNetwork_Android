package com.websoftq.socialnetwork.models


import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("Accesskey")
    val accesskey: String,
    @SerializedName("status_code")
    val statusCode: String,
    @SerializedName("status_message")
    val statusMessage: String,
    @SerializedName("sudentData")
    val sudentData: List<SudentData>
) {
    data class SudentData(
        @SerializedName("description")
        val description: String,
        @SerializedName("dob")
        val dob: String,
        @SerializedName("favourite_by_count")
        val favouriteByCount: Any,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("last_name")
        val lastName: String,
        @SerializedName("mobile")
        val mobile: String,
        @SerializedName("school")
        val school: String
    )
}