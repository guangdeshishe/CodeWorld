package com.agilezhu.common.util

/**
 * 带参数的单例模式
 * @param instanceClass 要实例化的单例类
 * @param paramClass 单例模式要传入的参数类
 * @sample 示例
 * class LoginServer private constructor(loginDataSource: LoginDataSource) {
 *   companion object : SingletonHolder<LoginServer, LoginDataSource>(::LoginServer)
 * }
 * 使用：LoginServer.getInstance(LoginDataSource())
 */
open class SingletonHolder<out instanceClass, in paramClass>(private val constructor: (paramClass) -> instanceClass) {
    @Volatile
    private var mInstance: instanceClass? = null

    fun getInstance(param: paramClass): instanceClass {
        return mInstance ?: synchronized(this) {
            mInstance ?: constructor(param).apply {
                mInstance = this
            }
        }
    }
}