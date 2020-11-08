package com.websoftq.socialnetwork.models


import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status_code")
    val statusCode: String,
    @SerializedName("status_message")
    val statusMessage: String
) {
    data class Data(
        @SerializedName("references")
        val references: References,
        @SerializedName("student_images")
        val studentImages: StudentImages?,
        @SerializedName("student_info")
        val studentInfo: StudentInfo
    ) {
        class References(
        )

        data class StudentImages(
            @SerializedName("image_path")
            val imagePath: String? ="",
            @SerializedName("images")
            val images: List<Image>? = listOf()
        ) {
            data class Image(
                @SerializedName("caption")
                val caption: Any,
                @SerializedName("image")
                val image: String,
                @SerializedName("sorting_index")
                val sortingIndex: String
            )
        }

        data class StudentInfo(
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
            val referenceCount: String,
            @SerializedName("school")
            val school: String,
            @SerializedName("student_id")
            val studentId: String,
            @SerializedName("username")
            val username: String
        )
    }
}