package com.xudre.marvelheroes.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xudre.marvelheroes.R

class CharacterListAdapter : RecyclerView.Adapter<CharacterListAdapter.CharacterItemViewHolder>() {
    inner class CharacterItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_character, parent, false);

        return CharacterItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holderCharacterItem: CharacterItemViewHolder, position: Int) {

    }
}

