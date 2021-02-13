package com.gunt.imagefinder.data.repository.network.response

import com.gunt.imagefinder.data.domain.ImageDocument

data class ResponseKakao(
    val meta: ResponseMeta,
    val documents: List<ImageDocument>
)
