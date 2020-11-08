package com.websoftq.socialnetwork.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.models.RandomUserResponse
import kotlinx.android.extensions.LayoutContainer
import java.util.*

class UsersAdapter(
    private val context: Context, var callBack: CallBack,
    private var userList: MutableList<RandomUserResponse.RandomUserList>
) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    val TAG = UsersAdapter::class.java.simpleName.toString()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_users, parent, false)
        val params = view.layoutParams
        //params.width = (rvCategoryHome-70) / 3
        // params.height = params.width
        //  view.layoutParams = params
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
        val user_lyt = containerView.findViewById<ConstraintLayout>(R.id.user_lyt)
        val tv_first_name = containerView.findViewById<TextView>(R.id.tv_first_name)
        val tv_description = containerView.findViewById<TextView>(R.id.tv_description)
        val tv_school_name = containerView.findViewById<TextView>(R.id.tv_school_name)
        val img_user = containerView.findViewById<SimpleDraweeView>(R.id.img_user)
        fun bind(item: RandomUserResponse.RandomUserList) {

            tv_first_name.text = item.firstName + " " + item.lastName
            tv_description.text = item.description
            tv_school_name.text = item.school
            img_user.setImageURI(item.image)
            user_lyt.setOnClickListener {
                callBack.itemClicks(it, item.id)
            }
        }
    }

    fun updateData(data:MutableList<RandomUserResponse.RandomUserList> ) {
        userList = data
        Log.e("TAG", "size-" + data.size)
        notifyDataSetChanged()
    }
    interface CallBack {
        fun itemClicks(view: View, userId : String)
    }
}