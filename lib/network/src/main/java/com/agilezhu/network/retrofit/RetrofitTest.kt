package com.agilezhu.network.retrofit

import android.util.Log
import retrofit2.Retrofit

class RetrofitTest {
    companion object{
        fun test(){
            val retrofit = Retrofit.Builder().baseUrl("http://www.baidu.com/").build()
            val userServer = retrofit.create(IUserServer::class.java)
            val result = userServer.getUserInfo("")
            Log.i("testRetrofit",result.toString())
        }
    }
}