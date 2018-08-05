package com.thiago.rickandmortyapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.thiago.rickandmortyapplication.model.CharacterModel
import kotlinx.coroutines.experimental.launch

class PageKeyedCharacterDataSource(private val repository: RickAndMortyRepository) : PageKeyedDataSource<String, CharacterModel>() {
    val initialShowProgress = MutableLiveData<Boolean>()
    val error = MutableLiveData<Throwable>()


    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, CharacterModel>) {
        Log.i(TAG, "loadInitial")
        launch {
            loadInitialData(callback)
        }

    }

    private suspend fun loadData(callback: LoadCallback<String, CharacterModel>, params: LoadParams<String>) {
        try {
            val response = repository.getNextCharacters(params.key).await()
            val characters = response.results.map { it }
            callback.onResult(characters, response.info.next)
        } catch (e: Exception) {
            error.postValue(e)
        }

    }

    private suspend fun loadInitialData(callback: LoadInitialCallback<String, CharacterModel>) {
        initialShowProgress.postValue(true)
        try {
            val response = repository.getCharacters().await()
            val characters = response.results.map { it }
            callback.onResult(characters, response.info.prev, response.info.next)
        } catch (e: Exception) {
            error.postValue(e)
        } finally {
            initialShowProgress.postValue(false)
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, CharacterModel>) {
        launch {
            loadData(callback, params)
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, CharacterModel>) {

    }

    companion object {
        const val TAG = "PageKeyedCharacter"
    }
}