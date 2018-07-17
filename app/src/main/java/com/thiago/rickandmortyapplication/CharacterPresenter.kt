package com.thiago.rickandmortyapplication

import com.thiago.rickandmortyapplication.repository.RickAndMortyRepository
import javax.inject.Inject

class CharacterPresenter @Inject constructor(var mRetrofit: RickAndMortyRepository) : CharacterContract.Presenter {

    companion object {
        const val TAG = "CharacterPresenter"
    }
}