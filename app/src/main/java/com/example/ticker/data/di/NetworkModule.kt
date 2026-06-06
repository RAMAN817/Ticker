package com.example.ticker.data.di

import com.example.ticker.BuildConfig
import com.example.ticker.data.api.NewsApiService
import com.example.ticker.data.api.StockApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
//Lives in for lifetime
@InstallIn(SingletonComponent::class)
//object instead of class as there is no reason to create multiple instance
object NetworkModule {
    //KotlinJsonAdapterFactory let moshi understand kotlin data classes
    @Provides @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor { chain ->                       // your token interceptor
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("token", BuildConfig.FINNHUB_API_KEY)
                    .build()
                chain.proceed(chain.request().newBuilder().url(url).build())
            }
            .addInterceptor(logging)                         // logging — added AFTER token
            .build()
    }



    @Provides @Singleton
    fun provideRetrofit(moshi: Moshi , client: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl("https://finnhub.io/api/v1/")
            .client(client)
            //tells retrofit  how to convert JSON  response into kotlin class
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService =
        //looks into interface and generate a real working implementation
        retrofit.create(NewsApiService::class.java)

    @Provides @Singleton
    fun provideStockApiService(retrofit: Retrofit): StockApiService =
        retrofit.create(StockApiService::class.java)







}