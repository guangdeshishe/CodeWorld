package com.agilezhu.login.ui

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agilezhu.login.R
import com.agilezhu.login.data.LoginResult
import com.agilezhu.login.data.LoginServer

class LoginViewModel(val mLoginServer: LoginServer) : ViewModel() {
    //登录界面表单状态
    val mLoginFormStatus: MutableLiveData<LoginFormStatus> = MutableLiveData()

    //登录返回结果
    val mLoginResult: MutableLiveData<LoginResult> = MutableLiveData()

    /**
     * 登录操作
     */
    fun login(userName: String, password: String) {
        val result = mLoginServer.login(userName, password)
        mLoginResult.value = result
    }

    fun register(userName: String, password: String) {
        val result = mLoginServer.register(userName, password)
        mLoginResult.value = result
    }

    /**
     * 检查数据是否合法
     */
    fun checkFormData(userName: String?, password: String?) {
        val result = LoginFormStatus()
        if (!checkUserName(userName)) {
            result.mUserNameErrorHint = R.string.invalid_username
        } else if (!checkPassword(password)) {
            result.mPasswordErrorHint = R.string.invalid_password
        } else {
            result.isDataValid = true
        }
        mLoginFormStatus.value = result
    }

    /**
     * 检查用户名是否合法
     */
    private fun checkUserName(userName: String?): Boolean {
        if (userName == null) {
            return false
        }
        if (userName.trim().isEmpty()) {
            return false
        }
        if (userName.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(userName).matches()
        }
        return true
    }

    private fun checkPassword(password: String?): Boolean {
        return password != null && password.isNotEmpty()
    }
}