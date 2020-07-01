package com.xudre.marvelheroes.service.response

class PagedResponse<T> (
    val offset: Number,
    val limit: Number,
    val total: Number,
    val count: Number,
    val results: List<T>
)
