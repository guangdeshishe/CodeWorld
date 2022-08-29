package com.agilezhu.login.data

import com.agilezhu.store.greendao.DBManager
import com.agilezhu.store.greendao.entity.User
import com.agilezhu.store.greendao.generate.UserDao

/**
 * 登录相关数据源
 */
class LoginDataSource {
    val mDaoSession = DBManager.instance.mDaoSession

    fun register(userName: String, password: String): User {
        val user = User()
        user.name = userName
        user.password = password
        user.id = DBManager.instance.mDaoSession.insertOrReplace(user)
        return user
    }

    fun queryUser(userName: String, password: String): User? {
        var userInfo = queryLocalUser(userName, password)
        if (userInfo == null) {
            userInfo = queryNetUser(userName, password)
        }
        return userInfo
    }

    private fun queryLocalUser(userName: String, password: String): User? {
        return mDaoSession.userDao.queryBuilder()
            .where(UserDao.Properties.Name.eq(userName), UserDao.Properties.Password.eq(password))
            .build().unique()
    }

    private fun queryNetUser(userName: String, password: String): User? {
        return null
    }
}