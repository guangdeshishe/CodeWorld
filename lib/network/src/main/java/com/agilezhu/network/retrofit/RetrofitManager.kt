package com.agilezhu.network.retrofit

import com.agilezhu.network.processor.OkHttpManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object RetrofitManager {

    private val mRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://www.baidu.com/")
        .client(
            OkHttpManager.client
        )
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun getRetrofitTestServer(): IRetrofitTestServer {
        return mRetrofit.create(IRetrofitTestServer::class.java)
    }

    fun downloadFile(url:String,destDir:String,fileName:String,fileDownloadObserver: FileDownloadObserver<File>){
        getRetrofitTestServer()
            .downLoadFile(url)
            .subscribeOn(Schedulers.io())//subscribeOn和ObserOn必须在io线程，如果在主线程会出错
            .observeOn(Schedulers.io())
            .observeOn(Schedulers.computation())//需要
            .map { responseBody -> fileDownloadObserver.saveFile(responseBody, destDir, fileName); }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(fileDownloadObserver)
    }
}