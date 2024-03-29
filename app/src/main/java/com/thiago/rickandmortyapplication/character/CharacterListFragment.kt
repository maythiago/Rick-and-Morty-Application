package com.thiago.rickandmortyapplication.character


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
        ViewModelProviders.of(this).get(CharacterListViewModel::class.java)
    }

    private var columnCount = 1

    private val mAdapter by lazy {
        CharacterRecyclerViewAdapter(listener)
    }
    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateComponent()
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        presenter.showInitialLoading.observe(this, Observer {
            Log.i(TAG, "showloading $it")
            if (it) {
                rvCharacters.visibility = View.GONE
                tvEmptyList.visibility = View.GONE
                pbLoadingProgress.visibility = View.VISIBLE
            } else {
                pbLoadingProgress.visibility = View.GONE
            }
        })
        presenter.concertList.observe(this, Observer {
            rvCharacters.visibility = View.VISIBLE
            tvEmptyList.visibility = View.GONE
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