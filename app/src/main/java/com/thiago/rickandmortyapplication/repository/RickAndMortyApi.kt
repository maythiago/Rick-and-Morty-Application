package com.thiago.rickandmortyapplication.repository

import com.thiago.rickandmortyapplication.model.AllCharactersModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface RickAndMortyApi {
    @GET("character/")
    fun getCharacters(): Observable<AllCharactersModel>
    @GET
    fun getCharacters(@Url url: String? = null): Observable<AllCharactersModel>

}