package com.thiago.rickandmortyapplication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val baseApplication = application as BaseApplication
        onCreateComponent(baseApplication)
    }

    abstract fun onCreateComponent(baseApplication: BaseApplication)
}