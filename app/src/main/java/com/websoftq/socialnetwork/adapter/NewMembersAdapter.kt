package com.websoftq.socialnetwork.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.models.NewMemberResponse
import com.websoftq.socialnetwork.models.RandomUserResponse
import kotlinx.android.extensions.LayoutContainer

class NewMembersAdapter(
    private val context: Context,
    private val newMemberList: MutableList<NewMemberResponse.Data>
) :
    RecyclerView.Adapter<NewMembersAdapter.ViewHolder>() {
    val TAG = NewMembersAdapter::class.java.simpleName.toString()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_new_member, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val data = categoryList[position]

        holder.bind(newMemberList[position])
    }

    override fun getItemCount(): Int {
        return newMemberList.size
    }


    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        val imgUser = containerView.findViewById<SimpleDraweeView>(R.id.img_user)
        val userName = containerView.findViewById<TextView>(R.id.tv_member_name)
        val new_member_lyt = containerView.findViewById<ConstraintLayout>(R.id.new_member_lyt)

        fun bind(item: NewMemberResponse.Data) {
            imgUser.setImageURI(item.image)
            userName.text = item.username
        }
    }


}