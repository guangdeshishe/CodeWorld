package com.agilezhu.login.server

import android.content.Context
import com.agilezhu.common.arouter.service.IUserService
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * 使用@Autowired注解直接注入
 */
@Route(path = "/login/service/user")
class UserService : IUserService {
    override fun isLogin(): Boolean {
        return true
    }

    override fun init(context: Context?) {

    }
}