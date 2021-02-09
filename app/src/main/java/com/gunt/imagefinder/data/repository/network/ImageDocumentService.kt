package com.gunt.imagefinder.data.repository.network

import com.gunt.imagefinder.data.repository.network.model.ImageDocumentDto
import com.gunt.imagefinder.data.repository.network.response.ResponseKakao
import com.gunt.imagefinder.di.KAKAO_API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val REQUEST_IMAGE_LIST_SIZE_DEFAULT: Int = 50
const val REQUEST_IMAGE_LIST_TYPE_DEFAULT: String = "accuracy"

interface ImageDocumentService {
  @GET("/v2/search/image")
  @Headers("Authorization: KakaoAK $KAKAO_API_KEY")
  suspend fun requestImageDocs(
    @Query("query") title: String,
    @Query("sort") sort: String = REQUEST_IMAGE_LIST_TYPE_DEFAULT,
    @Query("page") page: Int,
    @Query("size") size: Int = REQUEST_IMAGE_LIST_SIZE_DEFAULT
  ): ResponseKakao<ImageDocumentDto>
}
