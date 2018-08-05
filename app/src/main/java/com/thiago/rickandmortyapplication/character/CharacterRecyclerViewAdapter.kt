package com.thiago.rickandmortyapplication.character


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.thiago.rickandmortyapplication.R
import com.thiago.rickandmortyapplication.injection.GlideApp
import com.thiago.rickandmortyapplication.model.CharacterModel

class CharacterRecyclerViewAdapter(
        private val mListener: OnListFragmentInteractionListener?)
    : PagedListAdapter<CharacterModel, CharacterViewHolder>(DIFF_CALLBACK) {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as CharacterModel
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_characterlist, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)!!
        GlideApp
                .with(holder.mView.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.empty_image)
                .error(R.drawable.rick_and_morty_error)
                .fitCenter()
                .into(holder.image)

        holder.title.text = item.name

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
