package com.xudre.marvelheroes.service.response

class ApiResponse<T> (
    val code: Number,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val etag: String,
    val data: PagedResponse<T>
)
