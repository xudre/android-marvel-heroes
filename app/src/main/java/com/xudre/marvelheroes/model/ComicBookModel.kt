package com.xudre.marvelheroes.model

import android.os.Parcelable
import com.xudre.marvelheroes.service.response.ComicResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ComicBookModel(
    val id: Number,
    val imageUrl: String,
    val title: String,
    val description: String?,
    val price: Number
): Parcelable {
    companion object {
        fun factory(data: ComicResponse): ComicBookModel {
            val path = data.thumbnail.path.replace("http:", "https:")

            return ComicBookModel(
                data.id,
                "${path}.${data.thumbnail.extension}",
                data.title,
                data.description,
                data.prices.firstOrNull()?.price ?: 0
            )
        }
    }
}
