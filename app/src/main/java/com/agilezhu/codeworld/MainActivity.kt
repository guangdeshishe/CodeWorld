package com.agilezhu.codeworld

import android.os.Bundle
import com.agilezhu.common.base.BaseActivity
import com.alibaba.android.arouter.launcher.ARouter

class MainActivity:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ARouter.getInstance().build("/login/LoginActivity").navigation()
    }
}