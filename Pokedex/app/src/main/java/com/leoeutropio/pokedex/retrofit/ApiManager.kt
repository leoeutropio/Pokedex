package com.leoeutropio.pokedex.retrofit


import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class ApiManager {
    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit
    private var context: Context? = null
    var endpoint = "https://pokeapi.co/api/v2/"

    constructor(context: Context?) {
        this.context = context
        val logInterceptor = MyHttpLoggingInterceptor()
        logInterceptor.setLevel(MyHttpLoggingInterceptor.Level.BODY)
        val interceptor: Interceptor = Interceptor { chain ->
            val original: Request = chain.request()
            val request: Request.Builder = original.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
            chain.proceed(request.build())
        }
        okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(30000 * 6, TimeUnit.MILLISECONDS)
            .readTimeout(30000 * 6, TimeUnit.MILLISECONDS)
            .writeTimeout(30000 * 6, TimeUnit.MILLISECONDS)
            .addInterceptor(logInterceptor)
            .addInterceptor(interceptor)
            .build()
        val gson: Gson = GsonBuilder()
            .create()
        retrofit = Retrofit.Builder()
            .baseUrl(endpoint)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    constructor(retrofit: Retrofit, okHttpClient: OkHttpClient) {
        this.retrofit = retrofit
        this.okHttpClient = okHttpClient
    }

    fun getRetrofit(): Retrofit {
        return retrofit
    }

    constructor(context: Context?, retrofit: Retrofit, okHttpClient: OkHttpClient) {
        this.context = context
        this.retrofit = retrofit
        this.okHttpClient = okHttpClient
    }
}