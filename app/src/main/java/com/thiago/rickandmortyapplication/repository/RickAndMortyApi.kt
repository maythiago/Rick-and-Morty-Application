package com.thiago.rickandmortyapplication.repository

import com.thiago.rickandmortyapplication.model.AllCharactersModel
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Url

interface RickAndMortyApi {
    @GET("character/")
    fun getCharacters(): Deferred<AllCharactersModel>
    @GET
    fun getCharacters(@Url url: String? = null): Deferred<AllCharactersModel>

}