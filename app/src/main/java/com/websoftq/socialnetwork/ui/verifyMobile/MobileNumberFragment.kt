package com.websoftq.socialnetwork.ui.verifyMobile

import Auth_Key
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.activities.MenuActivity
import com.websoftq.socialnetwork.models.VerifyMobileRequest
import com.websoftq.socialnetwork.ui.login.NewLoginViewModel
import kotlinx.android.synthetic.main.fragment_connection.*
import kotlinx.android.synthetic.main.fragment_mobile_number.*


class MobileNumberFragment : Fragment() {
    private lateinit var verifyMobileViewModel: VerifyMobileViewModel
    var userName: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var dateOfBirth: String = ""
    var schoolName: String = ""
    var imageArray: String = ""
    var description: String = ""
    var pasword: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mobile_number, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verifyMobileViewModel = ViewModelProviders.of(this).get(VerifyMobileViewModel::class.java)


        initView()
        getObservers()

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
        pasword = arguments?.getString(getString(R.string.password)).toString()
    }

    private fun initView() {
        tv_mobile_continue.setOnClickListener {
            setPassword(et_mobile_no.text.toString().trim())
        }
    }

    private fun getObservers() {
        verifyMobileViewModel.errorResponse.observe(requireActivity(), Observer {
            val view = activity?: return@Observer
            CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                it,
                requireContext()
            )        })

        verifyMobileViewModel.isLoading.observe(requireActivity(), Observer {
            if (it) {
                loader_mobile.visibility = View.VISIBLE
            } else {
                View.VISIBLE
                loader_mobile.visibility = View.GONE
            }
        })

        verifyMobileViewModel.getResponse.observe(requireActivity(), Observer {
            it?.let {
                if (it.statusMessage == "Success") {

                } else {
                    it.statusMessage.let { it1 ->
                        CommonUtils.showSnackBar(
                            view!!.findViewById(android.R.id.content),
                            it1,
                            requireContext()
                        )
                    }
                }
                //  }


            }
        })

    }

    private fun setPassword(mobileNo: String) {
        val view = activity ?: return
        when {
            mobileNo.isEmpty() -> CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                getString(R.string.enter_mobile_no),
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
                    getString(R.string.password) to pasword,
                    getString(R.string.mobile_number) to et_mobile_no.text.toString().trim(),
                    getString(R.string.verification_code) to "2357"

                )


                view.findNavController(R.id.host_fragment)
                    .navigate(R.id.action_mobileNumberFragment_to_verifiactionCodeFragment, bundle)


          //      verifyMobileViewModel.verifyMobile(Auth_Key, mobileNo)
            }
        }

    }

}