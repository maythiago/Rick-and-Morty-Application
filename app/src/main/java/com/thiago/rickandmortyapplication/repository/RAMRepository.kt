package com.thiago.rickandmortyapplication.repository

import com.thiago.rickandmortyapplication.injection.IRetrofitFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Retrofit
import javax.inject.Inject

class RAMRepository @Inject constructor(val retrofitFactory: IRetrofitFactory) {
    private var mRetrofit: Retrofit? = null
    private val api: RickAndMortyApi by lazy {
        mRetrofit = retrofitFactory.get()
        mRetrofit!!.create(RickAndMortyApi::class.java)
    }

    fun getCharacters(): Observable<AllCharactersRequest> {
        return api
                .getCharacters()
                .observeOn(AndroidSchedulers.mainThread())
    }
}