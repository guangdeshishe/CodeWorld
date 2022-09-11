package com.agilezhu.codeworld

import android.os.Bundle
import android.util.Log
import com.agilezhu.common.GlobalConstant
import com.agilezhu.common.base.BaseActivity
import com.agilezhu.common.util.QToast
import com.agilezhu.network.IHttpCallback
import com.agilezhu.network.IHttpProcessor
import com.agilezhu.network.annotation.BindOkHttp
import com.agilezhu.network.retrofit.RetrofitTest
import com.agilezhu.processor.generate.ARouterPage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    @BindOkHttp
    @Inject
    lateinit var httpProcessor: IHttpProcessor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ARouterPage.LoginActivity.navigation()

        RetrofitTest.test()

        val params = HashMap<String, Any>()
        params["param1"] = "param1"
        params["param2"] = "param2"
        httpProcessor.post("http://www.baidu.com", params,
            object : IHttpCallback {
                override fun onSuccess(result: String) {
                    Log.i(GlobalConstant.TAG, "okhttp:$result")
                }

                override fun onFail(errorInfo: String) {
                    QToast.show(errorInfo)
                    Log.i(GlobalConstant.TAG, "okhttp:$errorInfo")
                }
            })
    }
}