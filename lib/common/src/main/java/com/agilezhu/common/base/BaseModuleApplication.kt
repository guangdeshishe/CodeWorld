package com.agilezhu.common.base

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import com.agilezhu.common.GlobalConstant

/**
 * Base Application logic
 */
open class BaseModuleApplication {
    lateinit var mApplication: BaseApplication
    open fun attachBaseContext(base: Context?) {}
    open fun onCreate() {}
    open fun onTerminate() {}
    open fun onTrimMemory(level: Int) {}
    open fun onLowMemory() {}
    open fun onConfigurationChanged(newConfig: Configuration) {}
}