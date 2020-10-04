package com.saurabh.synerzipdemo.utils.extension

import android.text.TextUtils
import android.util.Log
import com.saurabh.synerzipdemo.SynerzipApp
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


/**
 * Created by SaurabhA on 03,October,2020
 *
 */


private val TAG = "Validation.kt"
val REGEX_VALID_MOBILE = "^(\\+91[\\-\\s]?)?[0]?(91)?[6789]\\d{9}$"
val REGEX_EMAIL =
    "^[\\w!#\$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#\$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}\$"
val REGEX_NAME = "[a-zA-Z][a-zA-Z ]*"

const val REGEX_REMOVE_SPECIAL_CHAR = "[^a-zA-Z0-9 ]"

fun isNull(string: String?): Boolean {
    return (string != null && !string.trim().isEmpty() && !string.trim()
        .equals("null", ignoreCase = true))
}

fun isValidString(string: String?): Boolean {
    return (string != null && string.trim().isNotEmpty())
}

fun isValidName(string: String?): Boolean {
    return (isValidString(string) && isValidMatch(REGEX_NAME, string ?: ""))
}

fun isEmptyList(list: List<Any>?): Boolean {
    return (list == null || list.isEmpty())
}

fun isValidList(list: List<*>?): Boolean {
    return list == null || list.size == 0
}

fun isValidMobileNumber(mobileNumber: String?): Boolean = try {
    if (!isValidString(mobileNumber))
        false
    else
        isValidMatch(REGEX_VALID_MOBILE, mobileNumber!!)
} catch (e: Exception) {
    Log.e(TAG, e.localizedMessage)
    false
}

fun isValidEmail(email: String): Boolean = try {
    isValidMatch(REGEX_EMAIL, email.trim())
} catch (e: Exception) {
    Log.e(TAG, e.localizedMessage)
    false
}


fun isValidMatch(pattern: String, sequence: String): Boolean = if (TextUtils.isEmpty(sequence)) {
    false
} else {
    try {
        val sPattern = Pattern.compile(pattern)
        val matcher = sPattern.matcher(sequence)
        matcher.matches()
    } catch (e: Exception) {
        false
    }
}

fun isValidAndEquals(firstStr: String?, compareWith: String, ignoreCase: Boolean): Boolean {
    return if (firstStr != null && !firstStr.trim().isEmpty() && !firstStr.trim().equals(
            "null",
            ignoreCase = ignoreCase
        )
    )
        if (ignoreCase) firstStr.equals(compareWith, ignoreCase = true) else firstStr.equals(
            compareWith
        )
    else
        false
}

fun getResString(id: Int): String {
    return if (id != 0) SynerzipApp.context.resources.getString(id) else ""
}

fun isListEmpty(list: List<*>?): Boolean {
    return list == null || list.isEmpty()
}


fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
    return input?.let(callback)
}

fun Map<String, String>.hasValidValue(key: String): Boolean {
    return try {
        (containsKey(key) && isValidString(get(key)))
    } catch (e: Exception) {
        false
    }
}

fun isValidCardExpiryDate(input: String?): Boolean {
    return if (isValidString(input)) {
        try {
            val simpleDateFormat = SimpleDateFormat("MM/yy")
            simpleDateFormat.isLenient = false
            val expiry = simpleDateFormat.parse(input)
            return (expiry.after(Date()) || (expiry.month == Date().month && expiry.year == Date().year))
        } catch (e: Exception) {
            false
        }
    } else false
}


fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean {
    return this != null && !isEmpty()
}


fun Any?.isOneOf(vararg values: Any): Boolean {
    run loop@{
        values.forEach {
            if (it == this)
                return true
        }
    }
    return false
}