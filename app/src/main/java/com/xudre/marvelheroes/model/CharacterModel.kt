package com.xudre.marvelheroes.model

import android.os.Parcelable
import com.xudre.marvelheroes.service.response.CharacterResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterModel(
    val id: Number,
    val imageUrl: String,
    val name: String,
    val description: String
): Parcelable {
    companion object {
        fun factory(data: CharacterResponse): CharacterModel {
            val path = data.thumbnail.path.replace("http:", "https:")

            return CharacterModel(
                data.id,
                "${path}.${data.thumbnail.extension}",
                data.name,
                data.description
            )
        }
    }
}
