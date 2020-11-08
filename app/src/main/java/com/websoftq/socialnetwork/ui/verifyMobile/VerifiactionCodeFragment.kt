package com.websoftq.socialnetwork.ui.verifyMobile

import Auth_Key
import UserPreferences
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
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.websoftq.meetfriends.projectView.retrofit.RestClient
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.activities.MenuActivity
import com.websoftq.socialnetwork.models.ArrayListAge
import com.websoftq.socialnetwork.models.SignUpResponse
import kotlinx.android.synthetic.main.fragment_mobile_number.*
import kotlinx.android.synthetic.main.fragment_verifiaction_code.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException


class VerifiactionCodeFragment : Fragment() {

    var userName: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var dateOfBirth: String = ""
    var schoolName: String = ""
    var imageArray: String = ""
    var description: String = ""
    var pasword: String = ""
    var verifiactionCode: String = ""
    var MobileNo: String = ""
    lateinit var userPreferences: UserPreferences


    private lateinit var verificationCodeViewModel: VerificationCodeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verifiaction_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verificationCodeViewModel =
            ViewModelProviders.of(this).get(VerificationCodeViewModel::class.java)
        userPreferences = UserPreferences(requireContext())
        getObservers()
        tv_validate_code.setOnClickListener {
            setCode(et_verification_code.text.toString().trim())
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
        pasword = arguments?.getString(getString(R.string.password)).toString()
        MobileNo = arguments?.getString(getString(R.string.mobile_number)).toString()
        verifiactionCode = arguments?.getString(getString(R.string.verification_code)).toString()

        Log.e(
            "Sign Up Data",
            userName + "-----" + firstName + " ------" + lastName + "----- " + dateOfBirth + "---- " + schoolName + " ----" +
                    imageArray + " " + description + "------" + pasword + "-----" + MobileNo + "------" + verifiactionCode
        )
    }

    private fun setCode(code: String) {
        val view = activity ?: return
        when {

            code.isEmpty() -> CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                getString(R.string.enter_verification_code),
                requireContext()
            )

            code != verifiactionCode -> {
                CommonUtils.showSnackBar(
                    view.findViewById(android.R.id.content),
                    getString(R.string.enter_valid_code),
                    requireContext()
                )
            }

            else -> {
              //  val images = imageArray.split(",").toTypedArray()
                val file = File(imageArray).path
                val img = file.toString()
              val img1 = img.split(",").toTypedArray()
                val images = RequestBody.create(MediaType.parse("image/*"), file)
                val frstName = RequestBody.create(MediaType.parse("text/plain"), firstName)
                val latName = RequestBody.create(MediaType.parse("text/plain"), lastName)
                val mono = RequestBody.create(MediaType.parse("text/plain"), MobileNo)
                val dobrt = RequestBody.create(MediaType.parse("text/plain"), "2020-09-15")
                val scName = RequestBody.create(MediaType.parse("text/plain"), schoolName)
                val schlCoordinate = RequestBody.create(MediaType.parse("text/plain"), "1121,454545")
                val pawrd = RequestBody.create(MediaType.parse("text/plain"), pasword)
                val descrip = RequestBody.create(MediaType.parse("text/plain"), description)
                val auth = RequestBody.create(MediaType.parse("text/plain"), Auth_Key)
                val pltform = RequestBody.create(MediaType.parse("text/plain"), "android")
                val dcId = RequestBody.create(MediaType.parse("text/plain"), "XXSSS")
                val usName = RequestBody.create(MediaType.parse("text/plain"), userName)


               /* verificationCodeViewModel.signUP(
                    mono, frstName, latName, dobrt, scName, schlCoordinate,
                    pawrd, descrip, auth, images, pltform, dcId, usName
                )*/


                verificationCodeViewModel.isLoading.value = true

                RestClient.getClient().signUp(mono, frstName, latName, dobrt, scName, schlCoordinate, pawrd, descrip, auth,
                    images, pltform, dcId, usName).enqueue(object :
                    Callback<SignUpResponse> {
                    override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                        verificationCodeViewModel.isLoading.value = false
                        if (t is IOException) {
                            verificationCodeViewModel.errorResponse.value =
                                requireContext().resources.getString(R.string.check_internet_connection)
                        } else {
                            verificationCodeViewModel.errorResponse.value = t.message
                        }
                    }

                    override fun onResponse(
                        call: Call<SignUpResponse>,
                        response: Response<SignUpResponse>
                    ) {
                        verificationCodeViewModel.isLoading.value = false
                        try {
                            if (response.body() != null) {
                                response.body()?.let {
                                    if (it.statusCode == "200") {
                                        verificationCodeViewModel.getSignUpResponse.value = it
                                    } else {
                                        verificationCodeViewModel.errorResponse.value = it.statusMessage
                                    }
                                }
                            } else {
                                response.errorBody()?.let {
                                    val jObjError = JSONObject(it.string())
                                    verificationCodeViewModel.errorResponse.value = jObjError.getString("message")
                                }
                            }
                        } catch (e: Exception) {
                            verificationCodeViewModel.errorResponse.value = e.toString()
                        }
                    }
                })
            }



               /* verificationCodeViewModel.signUP(
                    MobileNo, firstName, lastName, "2020-09-15 ", schoolName, "1121,454545",
                    pasword, description, Auth_Key, images, "android", "XXSSS", userName
                )*/


            }
        }



    private fun getObservers() {
        verificationCodeViewModel.errorResponse.observe(requireActivity(), Observer {
            val view = activity ?: return@Observer
            CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                it,
                requireContext()
            )
        })

        verificationCodeViewModel.isLoading.observe(requireActivity(), Observer {
            if (it) {
                loader_verify_code.visibility = View.VISIBLE
            } else {
                View.VISIBLE
                loader_verify_code.visibility = View.GONE
            }
        })

        verificationCodeViewModel.getSignUpResponse.observe(requireActivity(), Observer {
            it?.let {
                if (it.statusMessage == "Success") {
                    userPreferences.setLogin(true)

                    it.accesskey.let { it1 -> userPreferences.setAccessKey(it1) }
                    userPreferences.setUserName(it.sudentData[0].firstName + " " + it.sudentData[0].lastName)
                    it.sudentData[0].description.let { it1 -> userPreferences.setUserDescription(it1) }
                    it.sudentData[0].image.let { it1 -> userPreferences.setUserImage(it1) }
                    val intent = Intent(requireContext(), MenuActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
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
}