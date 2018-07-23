package com.thiago.rickandmortyapplication.character

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.thiago.rickandmortyapplication.R
import com.thiago.rickandmortyapplication.base.BaseApplication
import com.thiago.rickandmortyapplication.model.CharacterModel
import kotlinx.android.synthetic.main.fragment_characterlist_list.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CharacterListFragment.OnListFragmentInteractionListener] interface.
 */
class CharacterListFragment : Fragment(), CharacterListContract.View {
    private val presenter: CharacterListViewModel by lazy { ViewModelProviders.of(this).get(CharacterListViewModel::class.java) }

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateComponent()

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        var onCreate = presenter.onCreate()
        onCreate.networkErrors.observe(this, Observer {
            Toast.makeText(activity, it ?: "Ocorreu um erro", Toast.LENGTH_SHORT).show()
        })
        onCreate.data.observe(this, Observer { response ->
            val adapter = list.adapter as CharacterRecyclerViewAdapter
            response?.results?.let {
                adapter.addItems(it)
            }
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

        // Set the adapter
        if (view is RecyclerView) {
            view.let { x ->
                x.layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(x.context)
                    else -> GridLayoutManager(x.context, columnCount)
                }
                x.adapter = CharacterRecyclerViewAdapter(emptyList<CharacterModel>().toMutableList(),
                        listener)
                x.addItemDecoration(DividerItemDecoration(x.context, DividerItemDecoration.VERTICAL))

            }
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

