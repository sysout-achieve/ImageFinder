package com.gunt.imagefinder.ui.imagelist

sealed class ImageListEventType {
    object NewSearch : ImageListEventType()
    object NextPage : ImageListEventType()
    object NewFilter : ImageListEventType()
}
