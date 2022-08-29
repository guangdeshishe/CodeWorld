package com.agilezhu.store.greendao

import android.content.Context
import com.agilezhu.store.greendao.generate.DaoMaster
import com.agilezhu.store.greendao.generate.DaoSession

class DBManager private constructor() {
    private val DB_NAME = "common_db"
    private lateinit var mContext: Context
    lateinit var mDaoSession: DaoSession

    companion object {
        val instance: DBManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DBManager()
        }
    }

    fun init(context: Context) {
        mContext = context.applicationContext
        mDaoSession =
            DaoMaster(
                DaoMaster.DevOpenHelper(
                    context,
                    DB_NAME
                ).writableDatabase
            ).newSession()
    }
}