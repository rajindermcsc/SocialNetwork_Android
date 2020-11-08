package com.websoftq.socialnetwork.models


import com.google.gson.annotations.SerializedName

data class MyPhotosResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status_code")
    val statusCode: String,
    @SerializedName("status_message")
    val statusMessage: String
) {
    data class Data(
        @SerializedName("image_path")
        val imagePath: String,
        @SerializedName("images")
        val images: List<Image>
    ) {
        data class Image(
            @SerializedName("caption")
            val caption: Any,
            @SerializedName("image")
            val image: String,
            @SerializedName("sorting_index")
            val sortingIndex: String,
            @SerializedName("student_image_id")
            val studentImageId: String
        )
    }
}