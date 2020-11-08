package com.websoftq.socialnetwork.ui.addTopic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.activities.MenuActivity
import kotlinx.android.synthetic.main.fragment_topic.*




class TopicFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MenuActivity).hideBottomNavView()
        tv_validate_code.setOnClickListener {
            setTopic(et_topic.text.toString().trim())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MenuActivity).showBottoNavView()
    }


    private fun setTopic(et_topic: String) {
        val view = activity ?: return
        when {

            et_topic.isEmpty() -> CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                getString(R.string.enter_topic_name),
                requireContext()
            )
            else -> {

                val bundle = bundleOf(
                    getString(R.string.topic) to et_topic
                )
                view.findNavController(R.id.menu_nav_host_fragment).navigate(
                    R.id.action_topicFragment_to_takeVideoFragment, bundle)

            }
        }

    }

}