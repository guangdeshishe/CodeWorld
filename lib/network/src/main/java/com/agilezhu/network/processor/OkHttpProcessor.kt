package com.agilezhu.network.processor

import android.util.Log
import com.agilezhu.network.IHttpCallback
import com.agilezhu.network.IHttpProcessor

class OkHttpProcessor : IHttpProcessor {
    override fun post(url: String, params: Map<String, Any>, callback: IHttpCallback) {
        Log.i("testHilt", "okhttp发起了请求")
    }
}