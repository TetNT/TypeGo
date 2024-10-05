package com.tetsoft.typego.core.utils

import java.text.SimpleDateFormat
import java.util.Date

interface DateTimeFormatter {
    fun format(time: Long) : String

    class Standard : DateTimeFormatter {
        override fun format(time: Long): String {
            val dateFormat = SimpleDateFormat.getDateTimeInstance(
                SimpleDateFormat.DEFAULT,
                SimpleDateFormat.SHORT
            )
            return dateFormat.format(Date(time))
        }

    }
}