package com.example.cryptoapp.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.os.Build
import androidx.annotation.RequiresApi
import java.sql.Timestamp
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.N)
fun convertTimestampToTime(timestamp: Long?): String {
    if (timestamp == null) return ""
    val stamp = Timestamp(timestamp * 1000)
    val date = Date(stamp.time)
    val pattern = "HH:mm:ss"
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}
