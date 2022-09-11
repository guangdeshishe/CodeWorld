package com.agilezhu.network.processor

import com.agilezhu.network.IHttpCallback
import com.agilezhu.network.IHttpProcessor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class VolleyProcessor @Inject constructor() : IHttpProcessor {
    override fun post(url: String, params: Map<String, Any>, callback: IHttpCallback) {

    }
}