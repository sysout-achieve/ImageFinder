package com.gunt.imagefinder.data.repository.network

import com.gunt.imagefinder.data.domain.ImageDocument
import com.gunt.imagefinder.data.repository.ImageRepository
import com.gunt.imagefinder.data.repository.network.model.ImageDocumentDtoMapper
import com.gunt.imagefinder.data.repository.network.response.ResponseKakao
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class ImageRepositoryRemote
constructor(
  private var imageDocumentService: ImageDocumentService,
  private var mapper: ImageDocumentDtoMapper
) : ImageRepository {

  override suspend fun findImage(title: String, page: Int): ResponseKakao<ImageDocument> {
    val responseDocuments = imageDocumentService.requestImageDocs(title = title, page = page)
    return ResponseKakao(
      responseDocuments.meta,
      mapper.toDomainModelList(responseDocuments.documents)
    )
  }
}
