package com.xudre.marvelheroes.service.response

class ComicResponse (
    val id: Number,
    val title: String,
    val description: String?,
    val thumbnail: ImageResponse,
    val prices: List<ComicPriceResponse>
) : BaseResponse
