package com.agilezhu.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.agilezhu.login.data.LoginDataSource
import com.agilezhu.login.data.LoginServer

/**
 *LoginViewModel工厂类
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(LoginServer.getInstance(LoginDataSource())) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}