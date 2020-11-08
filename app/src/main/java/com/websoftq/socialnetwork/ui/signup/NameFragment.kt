package com.websoftq.socialnetwork.ui.signup

import android.content.Intent
import android.os.Bundle
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
import com.websoftq.socialnetwork.ui.login.NewLoginViewModel
import kotlinx.android.synthetic.main.fragment_connection.*
import kotlinx.android.synthetic.main.name_fragment.*
import kotlinx.android.synthetic.main.name_fragment.et_user_name

class NameFragment : Fragment() {

    private lateinit var nameViewModel: NameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.name_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameViewModel = ViewModelProviders.of(this).get(NameViewModel::class.java)

        tv_name_continue.setOnClickListener {
            signUpUser(
                et_user_name.text.toString().trim(),
                et_first_name.text.toString().trim(),
                et_last_name.text.toString().trim()
            )
        }

        getObservers()
    }


    private fun getObservers() {
        nameViewModel.errorResponse.observe(requireActivity(), Observer {
            val view = activity ?: return@Observer
            CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                it,
                requireContext()
            )
        })

        nameViewModel.isLoading.observe(requireActivity(), Observer {
            if (it) {
                name_view_loader.visibility = View.VISIBLE
            } else {
                View.VISIBLE
                name_view_loader.visibility = View.GONE
            }
        })

        nameViewModel.getResponse.observe(requireActivity(), Observer {
            val view = activity ?: return@Observer
            it?.let {
                if (it.statusMessage == "Success") {
                    val bundle = bundleOf(
                        getString(R.string.user_name) to et_user_name.text.toString().trim(),
                        getString(R.string.first_name) to et_first_name.text.toString().trim(),
                        getString(R.string.last_name) to et_last_name.text.toString().trim()
                    )
                    view.findNavController(R.id.host_fragment)
                        .navigate(R.id.action_nameFragment_to_birthdayFragment, bundle)
                } else {

                    it.statusMessage.let { it1 ->
                        CommonUtils.showSnackBar(
                            view.findViewById(android.R.id.content),
                            it1,
                            requireContext()
                        )
                    }
                }
            }
        })

    }

    private fun signUpUser(userName: String, firstName: String, password: String) {
        val view = activity ?: return
        when {
            userName.isEmpty() -> CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                getString(R.string.enter_user_name),
                requireContext()
            )
            firstName.isEmpty() -> CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                getString(R.string.enter_first_name),
                requireContext()
            )
            password.isEmpty() -> CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                getString(R.string.enter_last_name),
                requireContext()
            )

            else -> {
                nameViewModel.checkUserName(userName)

            }
        }

    }

}