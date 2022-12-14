package com.agilezhu.network

/**
 * 网络请求回调接口
 */
interface IHttpCallback {
    fun onSuccess(result: String)
    fun onFail(errorInfo: String)
}