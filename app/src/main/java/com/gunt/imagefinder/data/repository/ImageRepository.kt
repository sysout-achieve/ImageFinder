package com.gunt.imagefinder.data.repository

import com.gunt.imagefinder.data.domain.ImageDocument
import com.gunt.imagefinder.data.repository.network.response.ResponseKakao

interface ImageRepository {
  suspend fun findImage(title: String, page: Int): ResponseKakao<ImageDocument>
}
