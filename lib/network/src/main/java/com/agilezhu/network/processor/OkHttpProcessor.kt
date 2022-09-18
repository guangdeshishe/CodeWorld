package com.agilezhu.network.processor

import android.os.Handler
import android.os.Looper
import com.agilezhu.network.IHttpCallback
import com.agilezhu.network.IHttpProcessor
import okhttp3.*
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class OkHttpProcessor @Inject constructor() : IHttpProcessor {
    private val mOkHttpClient = OkHttpManager.client
    private val mMainHandler = Handler(Looper.getMainLooper())

    override fun post(url: String, params: Map<String, Any>, callback: IHttpCallback) {
        val requestBody = appendBody(params)
        val request = Request.Builder().url(url).post(requestBody).build()
        mOkHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val result = e.message ?: "Unknown error"
                mMainHandler.post {
                    callback.onFail(result)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body()?.string() ?: ""
                mMainHandler.post {
                    if (response.isSuccessful) {
                        callback.onSuccess(result)
                    } else {
                        callback.onFail(result)
                    }
                }
            }

        })
    }

    private fun appendBody(params: Map<String, Any>): RequestBody {
        val formBodyBuilder = FormBody.Builder()
        if (params.isNullOrEmpty()) {
            return formBodyBuilder.build()
        }
        for (param in params) {
            formBodyBuilder.add(param.key, param.value.toString())
        }
        return formBodyBuilder.build()
    }
}