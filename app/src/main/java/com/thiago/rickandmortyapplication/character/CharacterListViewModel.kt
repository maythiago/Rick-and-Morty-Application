package com.thiago.rickandmortyapplication.character

import android.arch.lifecycle.ViewModel
import com.thiago.rickandmortyapplication.repository.RequestResult
import com.thiago.rickandmortyapplication.model.AllCharactersModel
import com.thiago.rickandmortyapplication.repository.RickAndMortyRepository
import javax.inject.Inject

class CharacterListViewModel : ViewModel(), CharacterListContract.Presenter {
    @Inject
    lateinit var repository: RickAndMortyRepository

    fun onCreate(): RequestResult<AllCharactersModel> {
        return repository.getAllCharacters("")
    }

    companion object {
        const val TAG = "CharacterListViewModel"
    }
}