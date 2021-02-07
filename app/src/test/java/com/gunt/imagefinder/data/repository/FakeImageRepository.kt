package com.gunt.imagefinder.data.repository

import com.gunt.imagefinder.data.domain.ImageDocument
import com.gunt.imagefinder.data.repository.network.response.ResponseKakao
import com.gunt.imagefinder.data.repository.network.response.ResponseMeta

class FakeImageRepository constructor(private var imageList: MutableList<ImageDocument>) : ImageRepository {
    val responseMeta = ResponseMeta(10, 2, false)

    override suspend fun findImage(title: String, page: Int): ResponseKakao<ImageDocument> {
        return ResponseKakao(responseMeta, imageList)
    }
}
