package com.top.github.util

import com.top.github.trendingrepo.data.User
import java.util.*


val testUserA = User("natario1", "Mattia Iavarone", "user", "https://github.com/natario1", "https://avatars0.githubusercontent.com/u/15526561")

val testUserB = User("JakeWharton", "Jake Wharton", "user", "https://github.com/JakeWharton", "https://avatars2.githubusercontent.com/u/66577")

/**
 * [Calendar] object used for tests.
 */
val testCalendar: Calendar = Calendar.getInstance().apply {
    set(Calendar.YEAR, 1998)
    set(Calendar.MONTH, Calendar.SEPTEMBER)
    set(Calendar.DAY_OF_MONTH, 4)
}
