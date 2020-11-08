package com.websoftq.meetfriends.util

import android.util.Patterns
import androidx.lifecycle.MutableLiveData

object Validator {

    fun isEmailValid(email: MutableLiveData<String>?): Boolean {
        email ?: return false
        return Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }

    fun isPasswordValid(value: String?): Boolean {
        return value?.length in 6..30
    }

     fun isUserNameValid(value: String?): Boolean {
        val nameSize = value?.length ?: 0
        return nameSize != 0
    }

    fun isPhoneValid(value:String?):Boolean{
        return value?.length in 10..12
    }
}