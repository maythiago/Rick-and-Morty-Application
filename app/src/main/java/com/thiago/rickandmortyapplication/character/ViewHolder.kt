package com.thiago.rickandmortyapplication.character

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_characterlist.view.*

class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
    val image: ImageView = mView.ivAvatar
    val name: TextView = mView.tvTitle
    val specie: TextView = mView.tvSubtitle

    override fun toString(): String {
        return super.toString() + " '" + name.text + "'"
    }
}