package com.gunt.imagefinder.data.repository.network.model

data class ImageDocumentDto(
    val collection: String = "",
    val thumbnail_url: String = "",
    val image_url: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val display_sitename: String = "",
    val doc_url: String = "",
    val datetime: String = ""
)
