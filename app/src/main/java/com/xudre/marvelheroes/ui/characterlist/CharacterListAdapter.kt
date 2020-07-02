package com.xudre.marvelheroes.ui.characterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xudre.marvelheroes.R
import com.xudre.marvelheroes.model.CharacterModel

class CharacterListAdapter(private val picasso: Picasso) : RecyclerView.Adapter<CharacterListAdapter.CharacterItemViewHolder>() {

    val characters: MutableList<CharacterModel> = mutableListOf()

    var onCharacterTouched: ((position: Int) -> Unit)? = null

    inner class CharacterItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumbnail: ImageView = view.findViewById(R.id.iv_thumbnail)
        val name: TextView = view.findViewById(R.id.tv_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_character, parent, false);

        return CharacterItemViewHolder(view)
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: CharacterItemViewHolder, position: Int) {
        val character = characters[position]

        holder.name.text = character.name

        picasso.load(character.imageUrl)
//            .resize(
//                200,
//                200
//            )
//            .centerInside()
            .into(holder.thumbnail)

        holder.itemView.setOnClickListener {
            onCharacterTouched?.invoke(position)
        }
    }
}

