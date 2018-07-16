package com.thiago.rickandmortyapplication.repository

import io.reactivex.Observable
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character/")
    fun getCharacters(): Observable<AllCharactersRequest>
}