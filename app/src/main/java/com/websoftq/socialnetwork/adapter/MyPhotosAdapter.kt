package com.websoftq.socialnetwork.adapter

import IMAGE_URL
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.models.MyPhotosResponse
import com.websoftq.socialnetwork.models.UserDetailResponse
import kotlinx.android.extensions.LayoutContainer

class MyPhotosAdapter (
    private val context: Context,
    private val userList: MutableList<MyPhotosResponse.Data.Image>
) :
    RecyclerView.Adapter<MyPhotosAdapter.ViewHolder>() {
    val TAG = PhotosAdapter::class.java.simpleName.toString()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_photos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val data = categoryList[position]

        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        val img_user = containerView.findViewById<SimpleDraweeView>(R.id.img_user)
        fun bind(item: MyPhotosResponse.Data.Image) {
            img_user.setImageURI(IMAGE_URL + item.image)
        }
    }
}