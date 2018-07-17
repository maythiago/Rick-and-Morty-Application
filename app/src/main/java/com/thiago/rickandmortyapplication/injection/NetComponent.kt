package com.thiago.rickandmortyapplication.injection

import com.thiago.rickandmortyapplication.CharacterActivity
import com.thiago.rickandmortyapplication.character.CharacterListFragment
import com.thiago.rickandmortyapplication.character.CharacterListViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetModule::class, PresenterModule::class])
interface NetComponent {
    fun inject(characterActivity: CharacterActivity)
    fun inject(characterListFragment: CharacterListFragment)
    fun inject(presenter: CharacterListViewModel)

}