package com.websoftq.socialnetwork.models


import com.google.gson.annotations.SerializedName

data class RandomUserResponse(
    @SerializedName("data")
    val randomUserList: List<RandomUserList>,
    @SerializedName("status_code")
    val statusCode: String,
    @SerializedName("status_message")
    val statusMessage: String,
    @SerializedName("totalcount")
    val totalcount: Int
) {
    data class RandomUserList(
        @SerializedName("description")
        val description: String,
        @SerializedName("dob")
        val dob: String,
        @SerializedName("favourite_by_count")
        val favouriteByCount: Any,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("last_name")
        val lastName: String,
        @SerializedName("reference_count")
        val referenceCount: Int,
        @SerializedName("school")
        val school: String,
        @SerializedName("student_id")
        val studentId: String,
        @SerializedName("username")
        val username: String
    )
}