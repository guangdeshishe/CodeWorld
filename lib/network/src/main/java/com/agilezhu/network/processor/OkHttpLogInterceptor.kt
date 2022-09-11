package com.agilezhu.network.processor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import org.json.JSONObject
import java.nio.charset.Charset

/**
 * Logger for okhttp request
 */
class OkHttpLogInterceptor : Interceptor {
    private val TAG = "LogInterceptor"
    private val UTF8 = Charset.forName("UTF-8")
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBody = request.body()
        val url = chain.request().url()
        val msg = JSONObject()
        msg.put("method", request.method())
        msg.put("url", url)


        var requestParams = ""
        requestBody?.let {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset = UTF8
            val contentType = requestBody.contentType()
            contentType?.let {
                charset = contentType.charset(UTF8)
            }
            requestParams = buffer.readString(charset)
        }


        if (requestParams.isEmpty()) {
            requestParams = "无参数"
        }
        msg.put("requestParams", requestParams)

        val requestHeaders = request.headers().toString()
        msg.put("requestHeaders", requestHeaders)

        val response = chain.proceed(request)
        val responseBody = response.body()
        var bodyStrResponse = ""
        responseBody?.let {
            val bufferedSource = it.source()
            bufferedSource.request(Long.MAX_VALUE)
            val buffer = bufferedSource.buffer
            var charset = UTF8
            val contentType = responseBody.contentType()
            contentType?.let {
                charset = contentType.charset(UTF8)
            }
            bodyStrResponse = buffer.clone().readString(charset)
            if (bodyStrResponse.isEmpty()) {
                bodyStrResponse = "无响应body"
            }
        }

        msg.put("code", response.code())
        msg.put("responseHeaders", response.headers())
        msg.put("responseBody", bodyStrResponse)

        Log.d(TAG, "$msg")
        return response
    }
}