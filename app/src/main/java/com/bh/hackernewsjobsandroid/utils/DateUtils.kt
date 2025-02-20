package com.bh.hackernewsjobsandroid.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy, HH:mm", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}
