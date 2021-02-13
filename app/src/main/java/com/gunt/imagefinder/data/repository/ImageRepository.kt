package com.gunt.imagefinder.data.repository

import com.gunt.imagefinder.data.repository.network.response.ResponseKakao
import io.reactivex.rxjava3.core.Flowable

interface ImageRepository {
    fun findImage(title: String, page: Int): Flowable<ResponseKakao>
}
