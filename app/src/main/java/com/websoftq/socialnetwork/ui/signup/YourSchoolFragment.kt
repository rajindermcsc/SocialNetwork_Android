package com.websoftq.socialnetwork.ui.signup

import CommonUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener
import com.websoftq.socialnetwork.R
import kotlinx.android.synthetic.main.fragment_your_school.*


class YourSchoolFragment : Fragment() {

    var userName: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var dateOfBirth: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_your_school, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        userName = arguments?.getString(getString(R.string.user_name)).toString()
        firstName = arguments?.getString(getString(R.string.first_name)).toString()
        lastName = arguments?.getString(getString(R.string.last_name)).toString()
        dateOfBirth = arguments?.getString(getString(R.string.date_of_birthaday)).toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_indicate_continue.setOnClickListener {
            setSchoolName(places_autocomplete.text.toString().trim())
        }

        places_autocomplete.setOnPlaceSelectedListener {
            // do something awesome with the selected place
            places_autocomplete.setText(it.description.split(",")[0])
        }
    }


    private fun setSchoolName(schoolName: String) {
        val view = activity ?: return
        when {

            schoolName.isEmpty() -> CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                getString(R.string.indicate_school),
                requireContext()
            )


            else -> {
                val bundle = bundleOf(
                    getString(R.string.user_name) to userName,
                    getString(R.string.first_name) to firstName,
                    getString(R.string.last_name) to lastName,
                    getString(R.string.date_of_birthaday) to dateOfBirth,
                    getString(R.string.school_name) to schoolName
                )
                view.findNavController(R.id.host_fragment)
                    .navigate(R.id.action_yourSchoolFragment_to_addPictures, bundle)
            }
        }

    }


}