package com.xudre.marvelheroes.model

data class ComicBookModel(
    val id: Number,
    val imageUrl: String,
    val title: String,
    val description: String,
    val price: Number
)
