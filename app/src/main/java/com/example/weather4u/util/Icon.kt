package com.example.weather4u.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.InputStream


object Icon {
        fun getIcon(icon: String?): String {
            return "http://openweathermap.org/img/w/${icon}.png"
        }
    fun stringToBitMap(image: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(image, Base64.DEFAULT)
            val inputStream: InputStream = ByteArrayInputStream(encodeByte)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.message
            null
        }
    }
    }
