package com.websoftq.socialnetwork

import UserPreferences
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.websoftq.socialnetwork.activities.MenuActivity


class SplashFragment : Fragment() {
    lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userPreferences = UserPreferences(requireContext())
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private val startLoginCounter = object : CountDownTimer(1000, 1000) {
        override fun onFinish() {
            if (userPreferences.isAlreadyLogin()){

                    val intent = Intent(requireContext(), MenuActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()

            }else{
                val action = SplashFragmentDirections.actionSplashFragmentToLandingFragment()
                Navigation.findNavController(requireActivity(), R.id.host_fragment).navigate(action)
            }
        }
        override fun onTick(millisUntilFinished: Long) {
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startLoginCounter.start()
    }

}

