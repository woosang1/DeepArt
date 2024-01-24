
package com.example.deepart.utils

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast


private var toast : Toast? = null

fun Context.showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    if (!message.isNullOrEmpty()) {
        if (toast == null){
            toast = Toast.makeText(applicationContext, message, duration)
        }
        else{
            toast?.setText(message)
        }
        toast?.show()
    }
}

// 디바이스 너비
fun Context.getWidthDisplay(): Int {
    val metrics = resources.displayMetrics
    return metrics.widthPixels
}

// 디바이스 높이
fun Context.getHeightDisplay(): Int {
    val metrics = resources.displayMetrics
    return metrics.heightPixels
}

fun Context.isDarkMode() : Boolean = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

