package com.xudre.marvelheroes.ui.characterlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.squareup.picasso.Picasso
import com.xudre.marvelheroes.config.AppConfig
import com.xudre.marvelheroes.databinding.ListItemCharacterBinding
import com.xudre.marvelheroes.databinding.ListItemLoadingBinding
import com.xudre.marvelheroes.model.CharacterModel

class CharacterListAdapter(private val picasso: Picasso) : RecyclerView.Adapter<CharacterListAdapter.BaseViewHolder>() {

    private val loadingViewType = 99

    private val characters: MutableList<CharacterModel?> = mutableListOf()

    var onCharacterTouched: ((character: CharacterModel) -> Unit)? = null

    var thumbnailImageSize = AppConfig.THUMBNAIL_IMAGE_SIZE

    abstract inner class BaseViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun onBind(position: Int)
    }

    inner class CharacterItemViewHolder(private val binding: ListItemCharacterBinding) : BaseViewHolder(binding) {
        override fun onBind(position: Int) {
            val character = characters[position] ?: return

            binding.apply {
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

    inner class LoadingItemViewHolder(private val binding: ListItemLoadingBinding) : BaseViewHolder(binding) {
        override fun onBind(position: Int) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == loadingViewType) {
            val binding = ListItemLoadingBinding.inflate(inflater, parent, false)

            LoadingItemViewHolder(binding)
        } else {
            val binding = ListItemCharacterBinding.inflate(inflater, parent, false)

            CharacterItemViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (characters[position] == null) loadingViewType else super.getItemViewType(position)
    }

    fun removeLoading() {
        val position = characters.size - 1

        if (position < 0) return

        characters.removeAt(position)

        notifyItemRemoved(position)
    }

    fun addItems(items: List<CharacterModel>) {
        characters.removeAll { it == null }

        characters.addAll(items)

        characters.add(null)

        notifyDataSetChanged()
    }

    fun clearItems() {
        characters.clear()

        notifyDataSetChanged()
    }
}

