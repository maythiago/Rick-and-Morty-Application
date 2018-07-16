package com.thiago.rickandmortyapplication.base

import android.app.Application
import com.thiago.rickandmortyapplication.injection.AppModule
import com.thiago.rickandmortyapplication.injection.DaggerNetComponent
import com.thiago.rickandmortyapplication.injection.NetComponent
import com.thiago.rickandmortyapplication.injection.NetModule

class BaseApplication : Application() {
    var component: NetComponent? = null
    override fun onCreate() {
        super.onCreate()
        component = DaggerNetComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule("https://rickandmortyapi.com/api/"))
                .build()
    }

}
