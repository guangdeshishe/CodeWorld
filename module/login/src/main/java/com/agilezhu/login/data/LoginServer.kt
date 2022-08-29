package com.agilezhu.login.data

import com.agilezhu.common.util.SingletonHolder
import com.agilezhu.login.R

/**
 * 登录相关服务，登录、退出等功能；
 */
class LoginServer private constructor(private val mLoginDataSource: LoginDataSource) {
    companion object : SingletonHolder<LoginServer, LoginDataSource>(::LoginServer)

    fun login(userName: String, password: String): LoginResult {
        val loginResult = LoginResult()
        val userInfo = mLoginDataSource.queryUser(userName, password)
        if (userInfo == null) {
            loginResult.mErrorInfoRes = R.string.login_failed
        } else {
            loginResult.mUserInfo = userInfo
        }
        return loginResult
    }

    fun register(userName: String, password: String):LoginResult {
        val loginResult = LoginResult()
        loginResult.action = LoginResult.ACTION_REGISTER
        loginResult.mUserInfo = mLoginDataSource.register(userName, password)
        return loginResult
    }
}
