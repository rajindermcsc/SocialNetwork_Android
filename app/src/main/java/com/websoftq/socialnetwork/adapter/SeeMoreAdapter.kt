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
import com.websoftq.socialnetwork.models.NewMemberResponse
import com.websoftq.socialnetwork.models.RandomUserResponse
import kotlinx.android.extensions.LayoutContainer

class SeeMoreAdapter(
    private val context: Context,
    val callback: CallBack,
    private var newMemberList: MutableList<NewMemberResponse.Data>
) :
    RecyclerView.Adapter<SeeMoreAdapter.ViewHolder>() {
    val TAG = SeeMoreAdapter::class.java.simpleName.toString()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_see_more, parent, false)
        val params = view.layoutParams
        //params.width = (rvCategoryHome-70) / 3
        // params.height = params.width
        //  view.layoutParams = params
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val data = categoryList[position]

        holder.bind(newMemberList[position])
    }

    override fun getItemCount(): Int {
        return newMemberList.size
    }
    fun updateData(data:MutableList<NewMemberResponse.Data> ) {
        newMemberList = data
        Log.e("TAG", "size-" + data.size)
        notifyDataSetChanged()
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        val user_main_lyt = containerView.findViewById<ConstraintLayout>(R.id.user_main_lyt)
        val img_user = containerView.findViewById<SimpleDraweeView>(R.id.img_user)
        val firstName = containerView.findViewById<TextView>(R.id.first_name)
        val schoolName = containerView.findViewById<TextView>(R.id.school_name)

        fun bind(item: NewMemberResponse.Data) {
            img_user.setImageURI(item.image)
            firstName.text = item.firstName
            schoolName.text = item.school
            user_main_lyt.setOnClickListener {
                callback.itemClick(it, item.id)
            }
        }
    }

    interface CallBack {
        fun itemClick(view: View, userId: String)
    }
}