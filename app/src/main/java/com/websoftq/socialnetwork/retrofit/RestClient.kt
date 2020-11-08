package com.websoftq.meetfriends.projectView.retrofit

import API
import BASE_URL
import com.google.gson.GsonBuilder
import com.websoftq.socialnetwork.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RestClient {
    companion object {
        private var retrofit: Retrofit? = null

        private val httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC })
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()

        private val imageHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC })
            .connectTimeout(200, TimeUnit.SECONDS)
            .readTimeout(200, TimeUnit.SECONDS).build()

        fun getClient(): API {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build()
            return retrofit!!.create(API::class.java)
        }
        var gson = GsonBuilder()
            .setLenient()
            .create()

        fun getUploadClient(): API {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(imageHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit!!.create(API::class.java)
        }

    }
}