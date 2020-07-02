package com.xudre.marvelheroes.repository

import com.xudre.marvelheroes.model.CharacterModel
import com.xudre.marvelheroes.model.ComicBookModel
import com.xudre.marvelheroes.util.Paged

interface MarvelRepository {
    fun getCharacters(
        page: Number,
        perPage: Number,
        onSuccess: (Paged<CharacterModel>?) -> Unit,
        onFail: (Throwable?) -> Unit,
        onFinish: () -> Unit
    )

    fun getComicsFromCharacter(
        characterId: Number,
        page: Number,
        perPage: Number,
        onSuccess: (Paged<ComicBookModel>?) -> Unit,
        onFail: (Throwable?) -> Unit,
        onFinish: () -> Unit
    )
}
