package com.mukesh.template.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mukesh.template.BuildConfig
import com.mukesh.template.networking.ApiParams
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * DI -> Dependency Injection
 * DI Module
 * */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Save Data in Cache
     * */
    @Provides
    @Singleton
    fun cache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, (5 * 1024 * 1024).toLong())


    /**
     * Provide Ok Http Client
     * */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache
    ): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(NetworkInterceptor.interceptor)
            .retryOnConnectionFailure(true)
            .cache(cache)
            .build()
    } else OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .addInterceptor(NetworkInterceptor.interceptor)
        .retryOnConnectionFailure(true)
        .cache(cache)
        .build()


    /**
     * Gson Provider
     * */
    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder().setLenient().create()


    /**
     * Provide Retrofit Instance
     * */
    @Provides
    @Singleton
    fun providesRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(UnSafeOkHttpClient.unsafeOkHttpClient)
        .client(okHttpClient)
        .build()


    /**
     * Provide Api Params Interface Instance
     * */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiParams =
        retrofit.create(ApiParams::class.java)

}