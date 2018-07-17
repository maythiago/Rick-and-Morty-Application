package com.thiago.rickandmortyapplication.model

import com.google.gson.annotations.SerializedName

data class InfoPaginationModel(@SerializedName("count") val count: Int,
                               @SerializedName("pages") val pages: Int,
                               @SerializedName("next") val next: String,
                               @SerializedName("prev") val prev: String)