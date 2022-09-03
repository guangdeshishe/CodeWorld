package com.agilezhu.codeworld

import android.util.Log
import com.agilezhu.common.GlobalConstant
import com.agilezhu.common.base.BaseApplication
import com.agilezhu.network.IHttpProcessor
import com.agilezhu.network.annotation.BindOkHttp
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : BaseApplication() {
    @BindOkHttp
    @Inject
    lateinit var httpProcessor: IHttpProcessor

    override fun onCreate() {
        super.onCreate()
        Log.i(GlobalConstant.TAG, "MainApplication onCreate")
    }

    override fun onRegisterModuleApplication() {
        registerApplication("com.agilezhu.login.LoginApplication")
    }

}