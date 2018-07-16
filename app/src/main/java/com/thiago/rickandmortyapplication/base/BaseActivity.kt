package com.thiago.rickandmortyapplication.base

import android.app.Activity
import android.os.Bundle

abstract class BaseActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val baseApplication = application as BaseApplication
        onCreateComponent(baseApplication)
    }

    abstract fun onCreateComponent(baseApplication: BaseApplication)
}