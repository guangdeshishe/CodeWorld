package com.agilezhu.network

/**
 * 网络请求实现者okhttp/volley/retrofit...
 */
interface IHttpProcessor {
    fun post(url:String,params:Map<String,Any>,callback: IHttpCallback)
}