package com.thiago.rickandmortyapplication.model

import com.google.gson.annotations.SerializedName

data class CharacterModel(@SerializedName("id") val id: Int,
                          @SerializedName("name") val name: String,
                          @SerializedName("image") val imageUrl: String,
                          @SerializedName("species") val species: String)