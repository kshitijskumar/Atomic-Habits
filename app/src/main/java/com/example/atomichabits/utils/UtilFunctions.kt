package com.example.atomichabits.utils

import android.content.Context
import android.widget.Toast

object UtilFunctions {

    fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(this, msg, duration).show()
    }
}