package com.agilezhu.network

import com.agilezhu.network.annotation.BindOkHttp
import com.agilezhu.network.annotation.BindVolley
import com.agilezhu.network.processor.OkHttpProcessor
import com.agilezhu.network.processor.VolleyProcessor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HttpProcessorModule {

    @BindOkHttp
    @Binds
    abstract fun bindOkHttp(okHttpProcessor: OkHttpProcessor): IHttpProcessor

    @BindVolley
    @Binds
    abstract fun bindVolley(volleyProcessor: VolleyProcessor): IHttpProcessor
}