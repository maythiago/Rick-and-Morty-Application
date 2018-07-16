package com.thiago.rickandmortyapplication

import android.util.Log
import com.thiago.rickandmortyapplication.repository.RAMRepository
import javax.inject.Inject

class MainPresenter @Inject constructor(var mRetrofit: RAMRepository) : MainContract.Presenter {

    fun onCreate(unit: () -> Unit) {
        var subscribe = mRetrofit
                .getCharacters()
                .doOnSubscribe { Log.i(TAG, "showProgress") }
                .doOnTerminate { Log.i(TAG, "hideProgress") }
                .subscribe(
                        { response ->
                            Log.i(TAG, response.toString())
                            unit.invoke()
                        },
                        { throwable ->
                            Log.i(TAG, "Ocorreu um erro ao buscar todos os personagens", throwable)
                            unit.invoke()
                        },
                        {

                        })

    }

    companion object {
        const val TAG = "MainPresenter"
    }
}