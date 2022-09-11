package com.agilezhu.network.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IUserServer {

    @FormUrlEncoded
    @POST("getUser/")
    fun getUserInfo(@Field("userId") userId:String):Call<String>
}