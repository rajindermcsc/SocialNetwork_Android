package com.websoftq.socialnetwork.ui.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.websoftq.socialnetwork.R
import kotlinx.android.synthetic.main.fragment_landing.*


class LandingFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_create_acc.setOnClickListener {

            val action = LandingFragmentDirections.actionLandingFragmentToNameFragment()
            Navigation.findNavController(requireActivity(), R.id.host_fragment).navigate(action)
        }

        tv_connection.setOnClickListener {
            val action = LandingFragmentDirections.actionLandingFragmentToConnectionFragment()
            Navigation.findNavController(requireActivity(), R.id.host_fragment).navigate(action)
        }
    }




}