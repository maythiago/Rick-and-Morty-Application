package com.thiago.rickandmortyapplication.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val baseApplication = application as BaseApplication
        onCreateComponent(baseApplication)
    }

    abstract fun onCreateComponent(baseApplication: BaseApplication)
}