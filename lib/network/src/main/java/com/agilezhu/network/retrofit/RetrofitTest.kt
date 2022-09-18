package com.agilezhu.network.retrofit

import android.util.Log
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RetrofitTest {
    companion object {
        val testServer = RetrofitManager.getRetrofitTestServer()

        fun testDynamicPath() {
            testServer.getByDynamicPath("你好").enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.i("testRetrofit Success", call.request().toString())
                    response.body()?.string()?.let { Log.i("testRetrofit", it) }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.i("testRetrofit Fail", call.request().toString())
                    t.message?.let { Log.i("testRetrofit", it) }
                }

            })
        }

        fun testUploadSingleFile() {
            val file1 = File("image1.jpg")
            val file2 = File("image3.jpg")
            val file3 = File("text.txt")

            val params = MultipartBody.Builder()
                .addFormDataPart("param1", "this is test upload file")
                .addFormDataPart(
                    "paramNameFile1",
                    file1.name,
                    RequestBody.create(MultipartBody.FORM, file1)
                )
                .addFormDataPart(
                    "paramNameFile2",
                    file2.name,
                    RequestBody.create(MultipartBody.FORM, file2)
                )
                .addFormDataPart(
                    "paramNameFile3",
                    file3.name,
                    RequestBody.create(MultipartBody.FORM, file3)
                )
                .build()

            testServer.uploadFile(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {
                        //准备分发之前
                        Log.i("testRetrofit", "onSubscribe")
                    }

                    override fun onNext(t: String) {
                        Log.i("testRetrofit", "onNext:$t")
                    }

                    override fun onError(e: Throwable) {
                        //请求错误
                        Log.i("testRetrofit", "onError:$e")
                    }

                    override fun onComplete() {
                        //结束操作
                        Log.i("testRetrofit", "onComplete")
                    }
                })
        }

        fun testDownloadFile(){
            RetrofitManager.downloadFile("https://img0.baidu.com/it/u=3492148253,2406562344&fm=253&fmt=auto&app=138&f=GIF?w=478&h=766","/sdcard/download","test.jpg",object:FileDownloadObserver<File>(){
                override fun onDownLoadSuccess(t: File) {
                    Log.i("testRetrofit", "onDownLoadSuccess")
                }

                override fun onDownLoadFail(throwable: Throwable?) {
                    Log.i("testRetrofit", "onDownLoadFail:$throwable")
                }

                override fun onProgress(progress: Int, total: Long) {
                    Log.i("testRetrofit", "onProgress:$progress / $total")
                }

            })
        }

        fun test() {
            testDownloadFile()

        }
    }
}