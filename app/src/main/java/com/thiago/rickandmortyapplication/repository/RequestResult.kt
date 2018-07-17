package com.thiago.rickandmortyapplication.repository

import android.arch.lifecycle.LiveData

data class RequestResult<T>(
        val data: LiveData<T>,
        val networkErrors: LiveData<String>)