package com.websoftq.socialnetwork.ui.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.websoftq.socialnetwork.R
import kotlinx.android.synthetic.main.fragment_description.*


class DescriptionFragment : Fragment() {

    var userName: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var dateOfBirth: String = ""
    var schoolName: String = ""
    var imageArray: String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        Log.e("Image Array", imageArray)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userName = arguments?.getString(getString(R.string.user_name)).toString()
        firstName = arguments?.getString(getString(R.string.first_name)).toString()
        lastName = arguments?.getString(getString(R.string.last_name)).toString()
        dateOfBirth = arguments?.getString(getString(R.string.date_of_birthaday)).toString()
        schoolName = arguments?.getString(getString(R.string.school_name)).toString()
        imageArray = arguments?.getString(getString(R.string.add_pictures)).toString()

        tv_perposal_text.text = "Hi everybody ! I’m a student at "+schoolName+", nice to meet you I hope to meet new people and share activities " +
                "Here to discover and why not share togetherCurious to meet new people in the community"



        tv_description_continue.setOnClickListener {
            addDescription(et_description_name.text.toString().trim())
        }

        schools_lyt.setOnClickListener {
            et_description_name.setText(tv_perposal_text.text.toString())
        }
    }


    private fun addDescription(description: String) {
        val view = activity ?: return
        when {

            description.isEmpty() -> CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                getString(R.string.enter_description),
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
                    getString(R.string.description) to description
                )


                view.findNavController(R.id.host_fragment)
                    .navigate(R.id.action_descriptionFragment_to_passwordFragment, bundle)
            }
        }

    }


}