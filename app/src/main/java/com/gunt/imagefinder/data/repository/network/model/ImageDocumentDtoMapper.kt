package com.gunt.imagefinder.data.repository.network.model

import com.gunt.imagefinder.data.domain.ImageDocument
import com.gunt.imagefinder.data.repository.DomainMapper

class ImageDocumentDtoMapper : DomainMapper<ImageDocumentDto, ImageDocument> {

  override fun mapToDomainModel(model: ImageDocumentDto): ImageDocument =
    ImageDocument(
      collection = model.collection,
      thumbnail_url = model.thumbnail_url,
      image_url = model.image_url,
      width = model.width,
      height = model.height,
      display_sitename = model.display_sitename,
      doc_url = model.doc_url,
      datetime = model.datetime
    )

  override fun mapFromDomainModel(domainModel: ImageDocument): ImageDocumentDto =
    ImageDocumentDto(
      collection = domainModel.collection,
      thumbnail_url = domainModel.thumbnail_url,
      image_url = domainModel.image_url,
      width = domainModel.width,
      height = domainModel.height,
      display_sitename = domainModel.display_sitename,
      doc_url = domainModel.doc_url,
      datetime = domainModel.datetime
    )

  fun toDomainModelList(initial: List<ImageDocumentDto>): List<ImageDocument> =
    initial.map { mapToDomainModel(it) }

  fun fromDomainModelList(initial: List<ImageDocument>): List<ImageDocumentDto> =
    initial.map { mapFromDomainModel(it) }
}
