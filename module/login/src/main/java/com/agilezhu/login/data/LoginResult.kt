package com.agilezhu.login.data

import com.agilezhu.store.greendao.entity.User

/**
 * 登录返回结果
 */
class LoginResult {
    var mUserInfo: User? = null //返回用户信息
    var mErrorInfoRes: Int? = null //错误信息
    var action = ACTION_LOGIN
    companion object{
        val ACTION_LOGIN = 0
        val ACTION_REGISTER = 1
    }

    fun isSuccess(): Boolean {
        return mUserInfo != null
    }
}