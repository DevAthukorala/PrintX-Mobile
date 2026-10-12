package com.example.dewminiproject.util

import android.util.Patterns

object ValidationUtils {
    
    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Validates Sri Lankan phone numbers.
     * Supports formats like 07XXXXXXXX, +947XXXXXXXX, 947XXXXXXXX
     */
    fun isValidPhone(phone: String): Boolean {
        val regex = Regex("^(?:0|94|\\+94)?7(?:[0125678]\\d{7})$")
        return phone.isNotEmpty() && regex.matches(phone)
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}
