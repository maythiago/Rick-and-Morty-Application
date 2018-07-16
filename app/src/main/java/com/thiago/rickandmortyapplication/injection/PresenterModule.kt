package com.thiago.rickandmortyapplication.injection

import android.app.Application
import com.thiago.rickandmortyapplication.MainPresenter
import com.thiago.rickandmortyapplication.repository.RAMRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun providesMainPresenter(repository: RAMRepository): MainPresenter {
        return MainPresenter(repository)
    }
}