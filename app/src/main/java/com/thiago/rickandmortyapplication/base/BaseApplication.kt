package com.thiago.rickandmortyapplication.base

import android.app.Application
import com.thiago.rickandmortyapplication.injection.AppModule
import com.thiago.rickandmortyapplication.injection.DaggerNetComponent
import com.thiago.rickandmortyapplication.injection.NetComponent
import com.thiago.rickandmortyapplication.injection.NetModule

open class BaseApplication : Application() {
    var component: NetComponent? = null
    override fun onCreate() {
        super.onCreate()
        // TODO Actually the baseUrl is setted manually in instrumentation test
        component = DaggerNetComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule("http://localhost:8080/"/*"https://rickandmortyapi.com/api/"*/))
                .build()
    }

}
