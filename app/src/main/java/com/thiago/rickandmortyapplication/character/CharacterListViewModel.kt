package com.thiago.rickandmortyapplication.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.thiago.rickandmortyapplication.model.CharacterModel
import com.thiago.rickandmortyapplication.repository.CharacterDataSourceFactory
import com.thiago.rickandmortyapplication.repository.RickAndMortyRepository
import javax.inject.Inject

class CharacterListViewModel : ViewModel(), CharacterListContract.Presenter {
    @Inject
    lateinit var repository: RickAndMortyRepository

    val dataSourceFactory: CharacterDataSourceFactory  by lazy { CharacterDataSourceFactory(repository) }
    val concertList: LiveData<PagedList<CharacterModel>> by lazy { LivePagedListBuilder(dataSourceFactory, 25).build() }

    /**
     * val myConcertDataSource : DataSource.Factory<Int, Concert> =
    concertDao.concertsByDate()

    val concertList = LivePagedListBuilder(
    myConcertDataSource, /* page size */ 20).build()
     *
     * */
    fun onCreate(){
    }

    companion object {
        const val TAG = "CharacterListViewModel"
    }
}