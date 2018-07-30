package com.thiago.rickandmortyapplication.character

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thiago.rickandmortyapplication.R
import com.thiago.rickandmortyapplication.injection.GlideApp


import com.thiago.rickandmortyapplication.model.CharacterModel

class CharacterRecyclerViewAdapter(
        private val mListener: OnListFragmentInteractionListener?)
    : PagedListAdapter<CharacterModel, ViewHolder>(DIFF_CALLBACK) {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as CharacterModel
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_characterlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.name.text = item.name
        GlideApp
                .with(holder.mView.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.rick_and_morty_error)
                .error(R.drawable.rick_and_morty_error)
                .centerInside()
                .into(holder.image)

        holder.specie.text = item.species
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }

    }

    fun clear() {
        currentList?.clear()
        notifyItemRangeRemoved(0, currentList?.size ?: 0)

    }

    companion object {
        private val DIFF_CALLBACK = object :
                DiffUtil.ItemCallback<CharacterModel>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldConcert: CharacterModel,
                                         newConcert: CharacterModel): Boolean =
                    oldConcert.id == newConcert.id

            override fun areContentsTheSame(oldConcert: CharacterModel,
                                            newConcert: CharacterModel): Boolean =
                    oldConcert == newConcert
        }
    }
}
