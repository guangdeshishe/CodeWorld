package com.agilezhu.network

import com.agilezhu.network.annotation.BindOkHttp
import com.agilezhu.network.annotation.BindVolley
import com.agilezhu.network.processor.OkHttpProcessor
import com.agilezhu.network.processor.VolleyProcessor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
open abstract class HttpProcessorModule {

    @BindOkHttp
    @Binds
    @Singleton
    abstract fun bindOkHttp(okHttpProcessor: OkHttpProcessor)

    @BindVolley
    @Binds
    @Singleton
    abstract fun bindVolley(volleyProcessor: VolleyProcessor)
}