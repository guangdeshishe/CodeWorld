package com.agilezhu.common.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

class QToast {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mContext: Context? = null
        fun init(context: Context) {
            mContext = context.applicationContext
        }

        fun show(message: String) {
            show(message, false)
        }

        fun show(message: String, isLong: Boolean) {
            val showTime = if (isLong) {
                Toast.LENGTH_LONG
            } else {
                Toast.LENGTH_SHORT
            }
            Toast.makeText(mContext, message, showTime).show()
        }
    }
}