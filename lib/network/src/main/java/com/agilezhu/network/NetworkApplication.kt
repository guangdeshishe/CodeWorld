package com.agilezhu.network

import android.app.Application
import com.agilezhu.network.annotation.BindOkHttp
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NetworkApplication : Application() {
    @BindOkHttp
    @Inject
    lateinit var httpProcessor: IHttpProcessor
}