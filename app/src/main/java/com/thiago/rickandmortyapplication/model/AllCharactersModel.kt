package com.thiago.rickandmortyapplication.model

import com.google.gson.annotations.SerializedName

data class AllCharactersModel(@SerializedName("info") val info: InfoPaginationModel,
                              @SerializedName("results") val results: Array<CharacterModel>)

