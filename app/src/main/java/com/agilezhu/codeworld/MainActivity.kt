package com.agilezhu.codeworld

import android.os.Bundle
import android.util.Log
import com.agilezhu.common.GlobalConstant
import com.agilezhu.common.arouter.service.IUserService
import com.agilezhu.common.base.BaseActivity
import com.agilezhu.common.util.QToast
import com.agilezhu.network.IHttpProcessor
import com.agilezhu.network.annotation.BindOkHttp
import com.agilezhu.processor.generate.ARouterPage
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    @BindOkHttp
    @Inject
    lateinit var httpProcessor: IHttpProcessor

    var mUserServer = ARouter.getInstance().build("/login/service/user").navigation() as IUserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(GlobalConstant.TAG, "isLogin:" + mUserServer.isLogin())


        XXPermissions.with(this)
            // 申请单个权限
//            .permission(Permission.RECORD_AUDIO)
            // 申请多个权限
            .permission(Permission.Group.STORAGE)
            // 设置权限请求拦截器（局部设置）
            //.interceptor(new PermissionInterceptor())
            // 设置不触发错误检测机制（局部设置）
            //.unchecked()
            .request(object : OnPermissionCallback {

                override fun onGranted(permissions: MutableList<String>, all: Boolean) {
                    if (!all) {
                        QToast.show("获取部分权限成功，但部分权限未正常授予")
                        return
                    }
                    ARouterPage.LoginActivity.navigation()
                    QToast.show("获取录音和日历权限成功")
                }

                override fun onDenied(permissions: MutableList<String>, never: Boolean) {
                    if (never) {
                        QToast.show("被永久拒绝授权，请手动授予录音和日历权限")
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(this@MainActivity, permissions)
                    } else {
                        QToast.show("获取录音和日历权限失败")
                    }
                }
            })


    }
}