package com.xudre.marvelheroes.util

class Paged<T>(
    val page: Number,
    val perPage: Number,
    val total: Number,
    val items: List<T>
)
