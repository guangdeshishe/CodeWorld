package com.agilezhu.common.arouter.service

import com.alibaba.android.arouter.facade.template.IProvider


interface IUserService : IProvider {
    fun isLogin(): Boolean
}