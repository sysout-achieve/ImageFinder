package com.gunt.imagefinder.data.repository.network.response

data class ResponseKakao<T>(
    val meta: ResponseMeta,
    val documents: List<T>
)
