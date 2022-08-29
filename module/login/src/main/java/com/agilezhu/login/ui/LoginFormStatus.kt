package com.agilezhu.login.ui

import androidx.annotation.StringRes

/**
 * 登录表单数据验证状态
 */
class LoginFormStatus() {
    @StringRes
    var mUserNameErrorHint: Int? = null //用户名验证错误提示

    @StringRes
    var mPasswordErrorHint: Int? = null //密码验证错误提示

    var isDataValid = false //数据是否校验成功
}