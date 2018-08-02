package com.thiago.rickandmortyapplication.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.thiago.rickandmortyapplication.CharacterPresenter
import com.thiago.rickandmortyapplication.model.CharacterModel

class PageKeyedCharacterDataSource(private val repository: RickAndMortyRepository) : PageKeyedDataSource<String, CharacterModel>() {
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, CharacterModel>) {
        Log.i(TAG, "loadInitial")
        repository.getCharacters()
                .doOnSubscribe { Log.i(CharacterPresenter.TAG, "showProgress") }
                .doOnTerminate { Log.i(CharacterPresenter.TAG, "hideProgress") }
                .subscribe(
                        { response ->
                            Log.i(CharacterPresenter.TAG, response.toString())
                            val characters = response.results.map { it }
                            callback.onResult(characters, response.info.prev, response.info.next)
                        },
                        { throwable ->
                            Log.i(CharacterPresenter.TAG, "Ocorreu um erro ao buscar todos os personagens", throwable)
                        },
                        {

                        })

    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, CharacterModel>) {
        repository.getNextCharacters(params.key)
                .doOnSubscribe { Log.i(CharacterPresenter.TAG, "showProgress") }
                .doOnTerminate { Log.i(CharacterPresenter.TAG, "hideProgress") }
                .subscribe(
                        { response ->
                            Log.i(CharacterPresenter.TAG, response.toString())
                            val characters = response.results.map { it }
                            callback.onResult(characters, response.info.next)

                        },
                        { throwable ->
                            Log.i(CharacterPresenter.TAG, "Ocorreu um erro ao buscar todos os personagens", throwable)
                        },
                        {

                        })
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, CharacterModel>) {

    }

    companion object {
        const val TAG = "PageKeyedCharacter"
    }
}