package com.thiago.rickandmortyapplication.repository

import com.thiago.rickandmortyapplication.model.AllCharactersModel
import io.reactivex.Observable
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character/")
    fun getCharacters(): Observable<AllCharactersModel>
}