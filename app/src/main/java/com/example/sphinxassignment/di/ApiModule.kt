package com.example.sphinxassignment.di

import android.app.Application
import android.content.Context
import androidx.databinding.library.BuildConfig

import com.example.sphinxassignment.network.api.apihelper.ApiHelper
import com.example.sphinxassignment.network.api.apihelperimpl.ApiHelperImpl
import com.example.sphinxassignment.network.api.apiservice.ApiService
import com.example.sphinxassignment.Utils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024 //10MB
        return Cache(File(application.cacheDir, "http-cache"), cacheSize)
    }

    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        var logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
        // set your desired log level
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return logging
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache, context: Context): OkHttpClient {
        val httpClient = OkHttpClient.Builder().cache(cache)
        try {
            httpClient.addInterceptor(Interceptor { chain ->
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .header("content-type", "application/json")
                    .method(original.method, original.body)
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            })

            /*val httpClient: OkHttpClient = OkHttpClient.Builder().cache(cache)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            httpClient.connectTimeout(5, TimeUnit.MINUTES)
            httpClient.readTimeout(5, TimeUnit.MINUTES)
            return httpClient.build()*/

        } catch (e: Exception) {
            e.printStackTrace()
            Utils.showToast(context, "Network Issue...!")
        }
        return httpClient
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, context: Context, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://bidfortask.winayak.com/api/")
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}