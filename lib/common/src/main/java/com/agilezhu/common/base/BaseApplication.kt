package com.agilezhu.common.base

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import com.agilezhu.common.BuildConfig
import com.agilezhu.common.GlobalConstant
import com.agilezhu.common.util.QToast
import com.agilezhu.store.greendao.DBManager
import com.alibaba.android.arouter.launcher.ARouter
import org.joor.Reflect

abstract class BaseApplication : Application() {
    val mModuleApplications = ArrayList<BaseModuleApplication>()
    override fun onCreate() {
        super.onCreate()
        Log.i(GlobalConstant.TAG, "BaseApplication onCreate")
        onRegisterModuleApplication()

        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this)
        DBManager.instance.init(this)
        QToast.init(this)
    }

    fun registerApplication(className: String) {
        Log.i(GlobalConstant.TAG, "registerApplication: " + className)
        val moduleApplication: BaseModuleApplication
        try {
            moduleApplication = Reflect.on(className).create().get()
        } catch (exception: Exception) {
            Log.i(GlobalConstant.TAG, "Application not found: " + className)
            return
        }
        moduleApplication.mApplication = this
        moduleApplication.onCreate()
        mModuleApplications.add(moduleApplication)
    }


    abstract fun onRegisterModuleApplication()


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        for (moduleApplication in mModuleApplications) {
            moduleApplication.attachBaseContext(base)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        for (moduleApplication in mModuleApplications) {
            moduleApplication.onTerminate()
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        for (moduleApplication in mModuleApplications) {
            moduleApplication.onTrimMemory(level)
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        for (moduleApplication in mModuleApplications) {
            moduleApplication.onLowMemory()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        for (moduleApplication in mModuleApplications) {
            moduleApplication.onConfigurationChanged(newConfig)
        }
    }
}