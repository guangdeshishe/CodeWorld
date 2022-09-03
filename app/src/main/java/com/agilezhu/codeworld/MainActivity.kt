package com.agilezhu.codeworld

import android.os.Bundle
import com.agilezhu.common.base.BaseActivity
import com.agilezhu.network.IHttpCallback
import com.agilezhu.processor.generate.ARouterPage

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ARouterPage.LoginActivity.navigation()

        (application as MainApplication ).httpProcessor.post("",HashMap<String,Any>(),object :IHttpCallback{
            override fun onSuccess(result: String) {
                TODO("Not yet implemented")
            }

            override fun onFail(errorInfo: String) {
                TODO("Not yet implemented")
            }
        })
    }
}