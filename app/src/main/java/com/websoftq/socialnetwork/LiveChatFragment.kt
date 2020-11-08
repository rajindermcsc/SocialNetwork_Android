package com.websoftq.socialnetwork

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_live_chat.*


class LiveChatFragment : Fragment() {
    private var isSelectIcon = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        select_icon.setOnClickListener {
            if (chat_option_layout.visibility == View.VISIBLE) {
                chat_option_layout.visibility = View.GONE
            } else {
                chat_option_layout.visibility = View.VISIBLE
            }
        }

        layoutAttach.setOnClickListener {
            if (attachment_option_layout.visibility == View.VISIBLE) {
                attachment_option_layout.visibility = View.GONE
            } else {
                attachment_option_layout.visibility = View.VISIBLE
            }
        }
    }
}