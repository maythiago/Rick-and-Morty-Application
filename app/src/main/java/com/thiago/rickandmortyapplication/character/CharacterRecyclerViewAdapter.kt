package com.thiago.rickandmortyapplication.character

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.thiago.rickandmortyapplication.R
import com.thiago.rickandmortyapplication.injection.GlideApp


import com.thiago.rickandmortyapplication.model.CharacterModel

import kotlinx.android.synthetic.main.fragment_characterlist.view.*

class CharacterRecyclerViewAdapter(
        private val mValues: MutableList<CharacterModel>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<CharacterRecyclerViewAdapter.ViewHolder>() {

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
        val item = mValues[position]
        holder.name.text = item.name
        GlideApp
                .with(holder.mView.context)
                .load(item.imageUrl)
                .error(R.drawable.rick_and_morty_error)
                .centerInside()
                .into(holder.image)

        holder.specie.text = item.species
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    fun addItems(items: Array<CharacterModel>) {
        mValues.addAll(items)
        notifyItemRangeInserted(itemCount, itemCount + items.size)
    }

    override fun getItemCount(): Int = mValues.size
    fun clear() {
        val count = itemCount
        mValues.clear()
        notifyItemRangeRemoved(0, count)
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val image: ImageView = mView.ivAvatar
        val name: TextView = mView.tvTitle
        val specie: TextView = mView.tvSubtitle

        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }
    }
}
