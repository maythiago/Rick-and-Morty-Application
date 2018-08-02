package com.thiago.rickandmortyapplication.repository

import android.annotation.SuppressLint

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.thiago.rickandmortyapplication.CharacterPresenter
import com.thiago.rickandmortyapplication.injection.IRetrofitFactory
import com.thiago.rickandmortyapplication.model.AllCharactersModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Retrofit
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(val retrofitFactory: IRetrofitFactory) {
    private var mRetrofit: Retrofit? = null
    private val api: RickAndMortyApi by lazy {
        mRetrofit = retrofitFactory.get()
        mRetrofit!!.create(RickAndMortyApi::class.java)
    }

    fun getCharacters(): Observable<AllCharactersModel> {
        return api
                .getCharacters()
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getNextCharacters(nextUrl: String): Observable<AllCharactersModel> {
        return api
                .getCharacters(nextUrl)
                .observeOn(AndroidSchedulers.mainThread())
    }


    @SuppressLint("CheckResult")
    fun getAllCharacters(query: String): RequestResult<AllCharactersModel> {
        val data = MutableLiveData<AllCharactersModel>()
        val errors = MutableLiveData<String>()
        getCharacters()
                .doOnSubscribe { Log.i(CharacterPresenter.TAG, "showProgress") }
                .doOnTerminate { Log.i(CharacterPresenter.TAG, "hideProgress") }
                .subscribe(
                        { response ->
                            Log.i(CharacterPresenter.TAG, response.toString())
                            data.value = response
                        },
                        { throwable ->
                            Log.i(CharacterPresenter.TAG, "Ocorreu um erro ao buscar todos os personagens", throwable)
                            errors.value = throwable.message
                        },
                        {

                        })
        return RequestResult(data, errors)
    }

}

