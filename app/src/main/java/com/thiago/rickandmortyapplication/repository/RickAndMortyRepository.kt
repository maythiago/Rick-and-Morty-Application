package com.thiago.rickandmortyapplication.repository

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.thiago.rickandmortyapplication.CharacterPresenter
import com.thiago.rickandmortyapplication.injection.IRetrofitFactory
import com.thiago.rickandmortyapplication.model.AllCharactersModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(val retrofitFactory: IRetrofitFactory) {
    private var mRetrofit: Retrofit? = null
    private val api: RickAndMortyApi by lazy {
        mRetrofit = retrofitFactory.get()
        mRetrofit!!.create(RickAndMortyApi::class.java)
    }

    fun getCharacters(): Call<AllCharactersModel> {
        return api
                .getCharacters()
    }

    @SuppressLint("CheckResult")
    fun getAllCharacters(query: String): RequestResult<AllCharactersModel> {
        val data = MutableLiveData<AllCharactersModel>()
        val errors = MutableLiveData<String>()
        getCharacters().enqueue(object : Callback<AllCharactersModel> {
            override fun onFailure(call: Call<AllCharactersModel>?, t: Throwable?) {
                errors.value = t?.message ?: "Ocorreu um erro ao buscar toso os personagens"
            }

            override fun onResponse(call: Call<AllCharactersModel>?, response: Response<AllCharactersModel>?) {
                Log.i(CharacterPresenter.TAG, response.toString())
                if (response?.isSuccessful == true) {
                    data.value = response.body()
                } else {
                    errors.value = response?.errorBody()?.string() ?: "Ocorreu um erro ao buscar toso os personagens"
                }
            }

        })

//        getCharacters()
//                .doOnSubscribe { Log.i(CharacterPresenter.TAG, "showProgress") }
//                .doOnTerminate { Log.i(CharacterPresenter.TAG, "hideProgress") }
//                .subscribe(
//                        { response ->
//                            Log.i(CharacterPresenter.TAG, response.toString())
//                            data.value = response
//                        },
//                        { throwable ->
//                            Log.i(CharacterPresenter.TAG, "Ocorreu um erro ao buscar todos os personagens", throwable)
//                            errors.value = throwable.message
//                        },
//                        {
//
//                        })
        return RequestResult(data, errors)
    }

}