package com.thiago.rickandmortyapplication.repository

import android.util.Log
import androidx.paging.PagedList
import com.thiago.rickandmortyapplication.model.AllCharactersModel
import javax.inject.Inject

class CharacterPagedCallback : PagedList.BoundaryCallback<AllCharactersModel>() {

    @Inject
    lateinit var repository: RickAndMortyRepository

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        Log.i(TAG, "onZeroItemsLoaded")
    }

    override fun onItemAtEndLoaded(itemAtEnd: AllCharactersModel) {
        Log.i(TAG, "onItemAtEndLoaded $itemAtEnd")
    }

    companion object {
        const val TAG = "CharacterPagedCallback"
    }
}

