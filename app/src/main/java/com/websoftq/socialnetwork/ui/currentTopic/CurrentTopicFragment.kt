package com.websoftq.socialnetwork.ui.currentTopic

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.adapter.ChatAdapter
import com.websoftq.socialnetwork.adapter.CurrentTopicAdapter
import com.websoftq.socialnetwork.models.CurrentTopicResponse
import com.websoftq.socialnetwork.ui.login.NewLoginViewModel
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_current_topic.*


class CurrentTopicFragment : Fragment(), CurrentTopicAdapter.CallBackCurrentTopic,
    CurrentTopicAdapter.CallBackCurrentTopicVideo {
    private val homeList: MutableList<CurrentTopicResponse.CurrentTopicList> = ArrayList()
    private lateinit var currentTopicViewModel: CurrentTopicViewModel
    private val currentTopicAdapter: CurrentTopicAdapter by lazy {
        CurrentTopicAdapter(requireContext(), this, homeList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_topic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentTopicViewModel = ViewModelProviders.of(this).get(CurrentTopicViewModel::class.java)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_current_topic.layoutManager = linearLayoutManager
        rv_current_topic.setHasFixedSize(true)
        rv_current_topic.adapter = currentTopicAdapter
        getObservers()
        getPost()
    }

    private fun getPost() {
        currentTopicViewModel.getCurrentTopic()
    }


    private fun getObservers() {
        currentTopicViewModel.errorResponse.observe(this.viewLifecycleOwner, Observer {
            val view = activity ?: return@Observer
            CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                it,
                requireContext()
            )
        })

        currentTopicViewModel.isLoading.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                loader_current_topic.visibility = View.VISIBLE
            } else {
                View.VISIBLE
                loader_current_topic.visibility = View.GONE
            }
        })

        currentTopicViewModel.getResponse.observe(this.viewLifecycleOwner, Observer {
            it?.let {
                if (it.statusCode == "200") {
                    if (it.currentTopicList.isEmpty()) {
                        emptyText.visibility = View.VISIBLE
                    } else {
                        homeList.clear()
                        emptyText.visibility = View.GONE
                        homeList.addAll(it.currentTopicList)
                        setView()
                    }

                }
            }
        })
    }

    private fun setView() {
        currentTopicAdapter.notifyDataSetChanged()
    }


    override fun itemClickCurrentTopic(view: View) {
        view.findNavController().navigate(R.id.action_currentTopicFragment_to_messageFragment)
    }

    override fun itemClickCurrentTopicVideo(view: View) {
        view.findNavController().navigate(R.id.action_currentTopicFragment_to_videoFragment)

    }
}