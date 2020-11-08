package com.websoftq.socialnetwork.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.websoftq.socialnetwork.R
import kotlinx.android.synthetic.main.fragment_password.*


class PasswordFragment : Fragment() {


    var userName: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var dateOfBirth: String = ""
    var schoolName: String = ""
    var imageArray: String = ""
    var description: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_pasword_continue.setOnClickListener {
            setPassword(et_password.text.toString().trim())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userName = arguments?.getString(getString(R.string.user_name)).toString()
        firstName = arguments?.getString(getString(R.string.first_name)).toString()
        lastName = arguments?.getString(getString(R.string.last_name)).toString()
        dateOfBirth = arguments?.getString(getString(R.string.date_of_birthaday)).toString()
        schoolName = arguments?.getString(getString(R.string.school_name)).toString()
        imageArray = arguments?.getString(getString(R.string.add_pictures)).toString()
        description = arguments?.getString(getString(R.string.description)).toString()
    }

    private fun setPassword(password: String) {
        val view = activity ?: return
        when {

            password.isEmpty() -> CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                getString(R.string.enter_password),
                requireContext()
            )


            else -> {
                val bundle = bundleOf(
                    getString(R.string.user_name) to userName,
                    getString(R.string.first_name) to firstName,
                    getString(R.string.last_name) to lastName,
                    getString(R.string.date_of_birthaday) to dateOfBirth,
                    getString(R.string.school_name) to schoolName,
                    getString(R.string.add_pictures) to imageArray,
                    getString(R.string.description) to description,
                    getString(R.string.password) to password
                )


                view.findNavController(R.id.host_fragment)
                    .navigate(R.id.action_passwordFragment_to_mobileNumberFragment, bundle)
            }
        }

    }
}