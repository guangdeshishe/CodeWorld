package com.agilezhu.login

import android.util.Log
import com.agilezhu.common.GlobalConstant
import com.agilezhu.common.base.BaseModuleApplication

class LoginApplication : BaseModuleApplication() {
    override fun onCreate() {
        super.onCreate()
        Log.i(GlobalConstant.TAG,"LoginApplication onCreate")
    }
}