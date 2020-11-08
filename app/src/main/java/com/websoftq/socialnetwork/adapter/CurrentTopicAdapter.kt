package com.websoftq.socialnetwork.adapter

import CommonUtils.retriveVideoFrameFromVideo
import VIDEO_URL
import android.R.attr.bitmap
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.drawee.view.SimpleDraweeView
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.models.CurrentTopicResponse
import com.websoftq.socialnetwork.utils.ResizableImageView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_take_video.*


class CurrentTopicAdapter(
    private val context: Context,
    val callBack: CallBackCurrentTopic,
    private val homeList: MutableList<CurrentTopicResponse.CurrentTopicList>
) :
    RecyclerView.Adapter<CurrentTopicAdapter.ViewHolder>() {
    val TAG = CurrentTopicAdapter::class.java.simpleName.toString()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_current_topic, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val data = categoryList[position]
        holder.bind(homeList[position])
    }

    override fun getItemCount(): Int {
        return homeList.size
    }


    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        val img_video_icon = containerView.findViewById<ImageView>(R.id.img_video_icon)
        val img_topic = containerView.findViewById<ResizableImageView>(R.id.img_topic)
        val tvTopicName = containerView.findViewById<TextView>(R.id.tv_topic_name)
        val tvTopicDescription = containerView.findViewById<TextView>(R.id.tv_topic_description)
        fun bind(item: CurrentTopicResponse.CurrentTopicList) {
            tvTopicName.text = item.topicName
            // img_topic.setImageURI(VIDEO_URL+item.topicVideo)
            tvTopicDescription.text = item.topicDescription



            Glide.with(context).asBitmap().load(VIDEO_URL + item.topicVideo).into(img_topic)

        /*    try {
                val bitmap =
                    retriveVideoFrameFromVideo()
                if (bitmap != null) {
                    img_topic.setImageBitmap(bitmap)
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
*/

        }
    }


    interface CallBackCurrentTopic {
        fun itemClickCurrentTopic(view: View)
    }

    interface CallBackCurrentTopicVideo {
        fun itemClickCurrentTopicVideo(view: View)
    }
}

