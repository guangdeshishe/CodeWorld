package com.agilezhu.login

import android.os.Bundle
import android.util.Log
import com.agilezhu.common.base.BaseActivity
import com.agilezhu.common.GlobalConstant
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = "/login/LoginActivity")
class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.i(GlobalConstant.TAG, "LoginActivity onCreate")
    }
}