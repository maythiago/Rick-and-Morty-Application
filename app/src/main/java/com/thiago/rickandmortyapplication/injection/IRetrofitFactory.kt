package com.thiago.rickandmortyapplication.injection

import retrofit2.Retrofit

interface IRetrofitFactory {
    fun get(): Retrofit
}