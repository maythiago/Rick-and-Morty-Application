package com.thiago.rickandmortyapplication.repository

import com.google.gson.annotations.SerializedName
import java.util.*

data class AllCharactersRequest(@SerializedName("info") val info: InfoRequest, @SerializedName("results") val results: Array<CharacterModel>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AllCharactersRequest

        if (info != other.info) return false
        if (!Arrays.equals(results, other.results)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = info.hashCode()
        result = 31 * result + Arrays.hashCode(results)
        return result
    }
}

data class InfoRequest(@SerializedName("count") val count: Int,
                       @SerializedName("pages") val pages: Int,
                       @SerializedName("next") val next: String,
                       @SerializedName("prev") val prev: String)

data class CharacterModel(@SerializedName("id") val id: Int,
                          @SerializedName("name") val name: String)