package com.example.playlistmaker.search.data

import android.icu.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

object DateFormater {

    fun trackTimeFormatter(time: Long): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(time)

    }

    fun trackDateFormatter(date: String): String {
        return SimpleDateFormat("yyyy")
            .parse(date)
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .year
            .toString()
    }
}