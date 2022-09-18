package com.agilezhu.network.retrofit

import androidx.annotation.NonNull
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface IRetrofitTestServer {

    /**
     * 以POST方式，表单的形式传递参数，需要配合 @FormUrlEncoded 和 @Field /@FieldMap使用
     */
    @FormUrlEncoded
    @POST("getUser/")
    fun postForm(@Field("userId") userId: String): Call<ResponseBody>

    /**
     * 以GET方式请求接口，需要配合 @Query / @QueryMap 使用
     */
    @GET("/?")
    fun getQuery(@Query("wd") key: String): Call<ResponseBody>

    /**
     * 动态请求链接,使用{}作为占位符，@Path作为要替换的path
     * encoded:表示当前传入的值是否URLEncode了，默认值是false
     * 需要注意的是{}占位符只能用于接口路径，而不能用于GET请求参数的占位符，否则会报下面的错误：
     * URL query string "xx={xx}" must not have replace block. For dynamic query parameters use @Query.
     */
    @GET("/{searchPath}/")
    fun getByDynamicPath(@Path(value = "searchPath", encoded = false) path: String): Call<ResponseBody>

    /**
     * 上传文件
     */
    @Multipart
    @POST("uploadSingleFile/")
    fun uploadFile( @Part("params") params:RequestBody):Observable<String>

    /**
     * 下载文件
     */
    @Streaming
    @GET
    fun downLoadFile(@NonNull @Url url: String): Observable<ResponseBody>
}