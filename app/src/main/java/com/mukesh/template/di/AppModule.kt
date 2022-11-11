package com.mukesh.template.di

import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mukesh.template.BuildConfig
import com.mukesh.template.networking.ApiParams
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
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
    fun cache(): Cache {
        val httpCacheDirectory =
            File(
                ExternalPreferredCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR,
                BuildConfig.APPLICATION_ID
            )
        return Cache(
            httpCacheDirectory,
            ExternalPreferredCacheDiskCacheFactory.DEFAULT_DISK_CACHE_SIZE.toLong()
        )
    }


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
            .addInterceptor(StatusCodeInterceptor.statusInterceptor)
            .retryOnConnectionFailure(true)
            .cache(cache)
            .build()
    } else OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .addInterceptor(StatusCodeInterceptor.statusInterceptor)
        .retryOnConnectionFailure(true)
        .cache(cache)
        .build()


    /**
     * Gson Provider
     * */
    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder().setLenient().serializeNulls().create()


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