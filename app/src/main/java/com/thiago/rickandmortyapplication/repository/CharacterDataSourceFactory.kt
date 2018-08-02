package com.thiago.rickandmortyapplication.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.thiago.rickandmortyapplication.model.CharacterModel

class CharacterDataSourceFactory(val repository: RickAndMortyRepository) : DataSource.Factory<String, CharacterModel>() {
    val sourceLiveData: MutableLiveData<PageKeyedCharacterDataSource> = MutableLiveData()

    override fun create(): DataSource<String, CharacterModel> {
        val source = PageKeyedCharacterDataSource(repository)
        sourceLiveData.postValue(source)
        return source
    }

}