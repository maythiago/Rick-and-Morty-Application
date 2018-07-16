package com.thiago.rickandmortyapplication.injection

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import com.google.gson.Gson
import javax.inject.Singleton
import dagger.Provides
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import android.app.Application
import com.thiago.rickandmortyapplication.repository.RAMRepository
import dagger.Module
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


@Module
class NetModule(var mBaseUrl: String) {


    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10L * 1024L * 1024L
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): IRetrofitFactory {
        return object : IRetrofitFactory {
            override fun get(): Retrofit {
                return Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.newThread()))
                        .baseUrl(mBaseUrl)
                        .client(okHttpClient)
                        .build()
            }
        }
    }

    @Provides
    @Singleton
    fun providesRAMRepository(retrofitFactory: IRetrofitFactory): RAMRepository {
        return RAMRepository(retrofitFactory)
    }
}