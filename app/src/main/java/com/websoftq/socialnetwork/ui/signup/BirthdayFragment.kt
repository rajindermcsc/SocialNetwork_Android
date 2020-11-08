package com.websoftq.socialnetwork.ui.signup

import android.app.DatePickerDialog
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
import kotlinx.android.synthetic.main.fragment_birthday.*
import java.text.SimpleDateFormat
import java.util.*


class BirthdayFragment : Fragment() {

    var userName: String = ""
    var firstName: String = ""
    var lastName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_birthday, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userName = arguments?.getString(getString(R.string.user_name)).toString()
        firstName = arguments?.getString(getString(R.string.first_name)).toString()
        lastName = arguments?.getString(getString(R.string.last_name)).toString()

        Log.e("Data", userName + firstName + lastName)
    }

    private fun initView() {
        et_bday.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            var month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                requireActivity(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in textbox
                    month = monthOfYear + 1
                    et_bday.text = "$dayOfMonth/$month/$year"
                },
                year,
                month,
                day
            )
            dpd.datePicker.maxDate = System.currentTimeMillis();
            dpd.show()

        }

        tv_continue_bday.setOnClickListener {
            selectBday(et_bday.text.toString().trim())
        }
    }

    private fun selectBday(bday: String) {
        val view = activity ?: return
        when {

            bday.isEmpty() -> CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                getString(R.string.select_bday),
                requireContext()
            )

            else -> {
                val bundle = bundleOf(
                    getString(R.string.user_name) to userName,
                    getString(R.string.first_name) to firstName,
                    getString(R.string.last_name) to lastName,
                    getString(R.string.date_of_birthaday) to et_bday.text.toString().trim()
                )
                view.findNavController(R.id.host_fragment).navigate(R.id.action_birthdayFragment_to_yourSchoolFragment, bundle)
            }
        }

    }

}