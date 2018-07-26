package com.thiago.rickandmortyapplication.injection

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thiago.rickandmortyapplication.BuildConfig
import com.thiago.rickandmortyapplication.IdlingResources
import com.thiago.rickandmortyapplication.repository.RickAndMortyRepository
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import javax.inject.Singleton


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
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
//        client.cache(cache)

        var client = builder.build()
        if (BuildConfig.DEBUG) {
            IdlingResources.registerOkHttp(client)
        }
        return client
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): IRetrofitFactory {
        return object : IRetrofitFactory {
            override fun get(): Retrofit {
                return Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create(gson))
//                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.newThread()))
                        .baseUrl(mBaseUrl)
                        .client(okHttpClient)
                        .build()
            }
        }
    }

    @Provides
    @Singleton
    fun providesRAMRepository(retrofitFactory: IRetrofitFactory): RickAndMortyRepository {
        return RickAndMortyRepository(retrofitFactory)
    }
}