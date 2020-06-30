package com.xudre.marvelheroes.model

data class CharacterModel(
    val id: Number,
    val name: String,
    val description: String,
    val comicBooks: List<ComicBookModel>
)
