package com.websoftq.socialnetwork.ui.login

import Auth_Key
import CommonUtils
import UserPreferences
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.activities.MenuActivity
import com.websoftq.socialnetwork.models.LoginRequest
import com.websoftq.socialnetwork.utils.MarshmallowPermissions
import com.websoftq.socialnetwork.utils.PERMISSIONS_REQUEST_CAMERA
import com.websoftq.socialnetwork.utils.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
import kotlinx.android.synthetic.main.fragment_connection.*


class ConnectionFragment : Fragment() {

    private lateinit var newLoginViewModel: NewLoginViewModel


    lateinit var userPreferences: UserPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connection, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newLoginViewModel = ViewModelProviders.of(this).get(NewLoginViewModel::class.java)
        userPreferences = UserPreferences(requireContext())
        getObservers()
        tv_set_connector.setOnClickListener {
            setConnector(et_user_name.text.toString().trim(), et_password.text.toString().trim())
        }

    }

    private fun getObservers() {
        newLoginViewModel.errorResponse.observe(requireActivity(), Observer {
            val view = activity ?: return@Observer
            CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                it,
                requireContext()
            )
        })

        newLoginViewModel.isLoading.observe(requireActivity(), Observer {
            if (it) {
                avi.visibility = View.VISIBLE
            } else {
                View.VISIBLE
                avi.visibility = View.GONE
            }
        })

        newLoginViewModel.getResponse.observe(requireActivity(), Observer {
            val view = activity ?: return@Observer
            it?.let {
                if (it.statusMessage == "Success") {
                    userPreferences.setLogin(true)
                    it.accesskey?.let { it1 -> userPreferences.setAccessKey(it1) }
                    userPreferences.setUserName(it.sudentData?.get(0)?.firstName + " " + it.sudentData?.get(0)?.lastName)
                    it.sudentData?.get(0)?.description?.let { it1 -> userPreferences.setUserDescription(it1) }
                    it.sudentData?.get(0)?.image?.let { it1 -> userPreferences.setUserImage(it1) }
                    val intent = Intent(requireActivity(), MenuActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {

                    it.statusMessage?.let { it1 ->
                        CommonUtils.showSnackBar(
                            view.findViewById(android.R.id.content),
                            it1,
                            requireContext()
                        )
                    }
                }
                //  }


            }
        })

    }

    private fun setConnector(userName: String, password: String) {
        val view = activity ?: return
        when {

            userName.isEmpty() -> CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                getString(R.string.enter_user_name),
                requireContext()
            )
            password.isEmpty() -> CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                getString(R.string.enter_password),
                requireContext()
            )

            else -> {

                newLoginViewModel.setLoging(Auth_Key, password, userName, "android")
            }
        }

    }

}