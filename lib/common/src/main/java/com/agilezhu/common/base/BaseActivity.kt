package com.agilezhu.common.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.agilezhu.common.GlobalConstant

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(GlobalConstant.TAG, "BaseActivity onCreate")
    }
}