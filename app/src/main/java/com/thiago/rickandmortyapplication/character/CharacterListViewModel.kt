package com.thiago.rickandmortyapplication.character

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.thiago.rickandmortyapplication.model.AllCharactersModel
import com.thiago.rickandmortyapplication.model.CharacterModel
import com.thiago.rickandmortyapplication.repository.CharacterDataSourceFactory
import com.thiago.rickandmortyapplication.repository.RequestResult
import com.thiago.rickandmortyapplication.repository.RickAndMortyRepository
import javax.inject.Inject

class CharacterListViewModel : ViewModel(), CharacterListContract.Presenter {
    @Inject
    lateinit var repository: RickAndMortyRepository

    val dataSourceFactory: DataSource.Factory<String, CharacterModel>  by lazy { CharacterDataSourceFactory(repository) }
    val concertList: LiveData<PagedList<CharacterModel>> by lazy { LivePagedListBuilder(dataSourceFactory, 25).build() }

    /**
     * val myConcertDataSource : DataSource.Factory<Int, Concert> =
    concertDao.concertsByDate()

    val concertList = LivePagedListBuilder(
    myConcertDataSource, /* page size */ 20).build()
     *
     * */
    fun onCreate(): RequestResult<AllCharactersModel> {
        return repository.getAllCharacters("")
    }

    companion object {
        const val TAG = "CharacterListViewModel"
    }
}