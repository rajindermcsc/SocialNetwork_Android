package com.websoftq.socialnetwork

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.websoftq.socialnetwork.activities.MenuActivity
import com.websoftq.socialnetwork.adapter.ChatAdapter
import com.websoftq.socialnetwork.adapter.NewMembersAdapter
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_community.*


class ChatFragment : Fragment(), ChatAdapter.CallBack {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MenuActivity).updateStatusBarColor("#00b0f0")

        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_chat.layoutManager = linearLayoutManager
        val chatAdapter = ChatAdapter(context!!, this)
        rv_chat.setHasFixedSize(true)
        rv_chat.adapter = chatAdapter
        rv_chat!!.adapter!!.notifyDataSetChanged()

    }

    override fun itemClick(view: View) {
        view.findNavController().navigate(R.id.action_destination_chat_to_liveChatFragment)
    }
}