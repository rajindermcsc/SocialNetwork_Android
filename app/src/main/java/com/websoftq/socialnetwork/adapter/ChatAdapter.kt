package com.websoftq.socialnetwork.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.websoftq.socialnetwork.R
import kotlinx.android.extensions.LayoutContainer

class ChatAdapter (private val context: Context, var callBack: CallBack) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    val TAG = ChatAdapter::class.java.simpleName.toString()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_chat, parent, false)
        val params = view.layoutParams
        //params.width = (rvCategoryHome-70) / 3
        // params.height = params.width
        //  view.layoutParams = params
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val data = categoryList[position]
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 7
    }


    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        val chat_lyt = containerView.findViewById<ConstraintLayout>(R.id.chat_lyt)
        fun bind(position: Int) {
            chat_lyt.setOnClickListener {
                callBack.itemClick(it)
            }
        }
    }

    interface CallBack {
        fun itemClick(view: View)
    }

}