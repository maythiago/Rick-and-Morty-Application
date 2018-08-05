package com.thiago.rickandmortyapplication.character

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_characterlist.view.*

class CharacterViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
    val image: ImageView = mView.ivAvatar
    val title : TextView = mView.tvTitle
}