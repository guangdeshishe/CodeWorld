package com.agilezhu.common.arouter

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.SerializationService
import com.alibaba.fastjson.JSON
import java.lang.reflect.Type

@Route(path = "/arouter/SerializationServiceImpl")
class SerializationServiceImpl : SerializationService {
    override fun init(context: Context?) {

    }

    override fun <T : Any?> json2Object(input: String?, clazz: Class<T>?): T {
        return JSON.parseObject(input, clazz)
    }

    override fun object2Json(instance: Any?): String {
        return JSON.toJSONString(instance)
    }

    override fun <T : Any?> parseObject(input: String?, clazz: Type?): T {
        Log.d("ARouterTest", "SerializationServiceImpl parseObject")
        return JSON.parseObject(input, clazz)
    }
}
