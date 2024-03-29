package com.thiago.rickandmortyapplication

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import com.thiago.rickandmortyapplication.base.BaseActivity
import com.thiago.rickandmortyapplication.base.BaseApplication
import com.thiago.rickandmortyapplication.character.CharacterListFragment
import com.thiago.rickandmortyapplication.character.OnListFragmentInteractionListener
import com.thiago.rickandmortyapplication.model.CharacterModel
import kotlinx.android.synthetic.main.character_activity.*
import javax.inject.Inject

class CharacterActivity : BaseActivity(), CharacterContract.View, OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: CharacterModel?) {
        Log.i(TAG, item.toString())
    }

    @Inject
    lateinit var presenter: CharacterPresenter

    override fun onCreateComponent(baseApplication: BaseApplication) {
        baseApplication.component!!.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_activity)

        val columnCount =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    3
                } else {
                    2
                }
        supportFragmentManager.beginTransaction().replace(rvCharacters.id, CharacterListFragment.newInstance(columnCount)).commit()

    }

    companion object {
        const val TAG = "CharacterActivity"
    }
}

