package com.thiago.rickandmortyapplication

import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import com.thiago.rickandmortyapplication.base.BaseActivity
import com.thiago.rickandmortyapplication.base.BaseApplication
import com.thiago.rickandmortyapplication.injection.DaggerNetComponent
import com.thiago.rickandmortyapplication.injection.NetComponent
import com.thiago.rickandmortyapplication.injection.NetModule
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {


    override fun onCreateComponent(baseApplication: BaseApplication) {
        baseApplication.component!!.inject(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(RelativeLayout(this))
        presenter.onCreate { Toast.makeText(this, "Chamou", Toast.LENGTH_SHORT).show() }
    }

    @Inject
    lateinit var presenter: MainPresenter
}

