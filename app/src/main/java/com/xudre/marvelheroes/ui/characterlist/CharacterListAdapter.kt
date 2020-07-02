package com.xudre.marvelheroes.ui.characterlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xudre.marvelheroes.databinding.ListItemCharacterBinding
import com.xudre.marvelheroes.model.CharacterModel

class CharacterListAdapter(private val picasso: Picasso) : RecyclerView.Adapter<CharacterListAdapter.CharacterItemViewHolder>() {

    val characters: MutableList<CharacterModel> = mutableListOf()

    var onCharacterTouched: ((character: CharacterModel) -> Unit)? = null

    var thumbnailImageSize = 200

    inner class CharacterItemViewHolder(val binding: ListItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCharacterBinding.inflate(inflater, parent, false)

        return CharacterItemViewHolder(binding)
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: CharacterItemViewHolder, position: Int) {
        val character = characters[position]

        holder.binding.apply {
            picasso.load(character.imageUrl)
                .resize(thumbnailImageSize, thumbnailImageSize)
                .centerInside()
                .into(ivThumbnail)

            tvName.text = character.name

            root.setOnClickListener {
                onCharacterTouched?.invoke(character)
            }
        }
    }
}

