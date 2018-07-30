package com.thiago.rickandmortyapplication.character

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.thiago.rickandmortyapplication.R
import com.thiago.rickandmortyapplication.base.BaseApplication
import kotlinx.android.synthetic.main.fragment_characterlist_list.*
import kotlinx.android.synthetic.main.fragment_characterlist_list.view.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CharacterListFragment.OnListFragmentInteractionListener] interface.
 */
class CharacterListFragment : Fragment(), CharacterListContract.View {
    private val presenter: CharacterListViewModel by lazy {
        ViewModelProviders.of(this).get(CharacterListViewModel::class.java)}

    private var columnCount = 1

    private val mAdapter by lazy {
        CharacterRecyclerViewAdapter(listener)
    }
    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateComponent()
        Log.i("CHARACTER", "BIIIIIRL")
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        var onCreate = presenter.onCreate()
        onCreate.networkErrors.observe(this, Observer {
            Toast.makeText(activity, it ?: "Ocorreu um erro", Toast.LENGTH_SHORT).show()

        })
        onCreate.data.observe(this, Observer { response ->

            if (response?.results?.isEmpty() != false) {
                if (mAdapter.itemCount > 0) {
                    mAdapter.clear()
                }
                rvCharacters.visibility = View.GONE
                tvEmptyList.visibility = View.VISIBLE
            } else {
                rvCharacters.visibility = View.VISIBLE
                tvEmptyList.visibility = View.GONE
            }
        })
        presenter.concertList.observe(this, Observer {
            mAdapter.submitList(it)
        })

    }


    private fun onCreateComponent() {
        var baseApplication = activity!!.application as BaseApplication
        baseApplication.component!!.inject(this)
        baseApplication.component!!.inject(presenter)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_characterlist_list, container, false)
        view.rvCharacters.let { x ->
            x.layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(x.context)
                else -> GridLayoutManager(x.context, columnCount)
            }
            x.adapter = mAdapter
            x.addItemDecoration(DividerItemDecoration(x.context, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val TAG = "CharacterListFragment"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                CharacterListFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}

