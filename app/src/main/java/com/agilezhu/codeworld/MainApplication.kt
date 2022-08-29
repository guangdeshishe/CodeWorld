package com.agilezhu.codeworld

import android.util.Log
import com.agilezhu.common.GlobalConstant
import com.agilezhu.common.base.BaseApplication

class MainApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        Log.i(GlobalConstant.TAG,"MainApplication onCreate")
    }

    override fun onRegisterModuleApplication() {
        registerApplication("com.agilezhu.login.LoginApplication")
    }

}