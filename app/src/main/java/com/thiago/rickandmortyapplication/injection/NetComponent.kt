package com.thiago.rickandmortyapplication.injection

import com.google.gson.Gson
import com.thiago.rickandmortyapplication.MainActivity
import dagger.Component
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetModule::class, PresenterModule::class])
interface NetComponent {
    fun inject(mainActivity: MainActivity)

}