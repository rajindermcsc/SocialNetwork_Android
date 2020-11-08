package com.websoftq.socialnetwork.models


import com.google.gson.annotations.SerializedName

data class CurrentTopicResponse(
    @SerializedName("data")
    val currentTopicList: ArrayList<CurrentTopicList>,
    @SerializedName("status_code")
    val statusCode: String,
    @SerializedName("status_message")
    val statusMessage: String,
    @SerializedName("videoUrl")
    val videoUrl: String
) {
    data class CurrentTopicList(
        @SerializedName("topic_description")
        val topicDescription: String,
        @SerializedName("topic_name")
        val topicName: String,
        @SerializedName("topic_video")
        val topicVideo: String
    )
}