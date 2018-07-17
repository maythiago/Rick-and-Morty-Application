package com.thiago.rickandmortyapplication.injection

import com.thiago.rickandmortyapplication.CharacterPresenter
import com.thiago.rickandmortyapplication.repository.RickAndMortyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun providesMainPresenter(repository: RickAndMortyRepository): CharacterPresenter {
        return CharacterPresenter(repository)
    }
}