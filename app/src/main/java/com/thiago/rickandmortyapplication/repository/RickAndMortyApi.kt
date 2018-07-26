package com.thiago.rickandmortyapplication.repository

import com.thiago.rickandmortyapplication.model.AllCharactersModel
import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character/")
    fun getCharacters(): Call<AllCharactersModel>
}