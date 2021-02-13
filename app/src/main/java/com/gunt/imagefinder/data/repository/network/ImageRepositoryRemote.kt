package com.gunt.imagefinder.data.repository.network

import com.gunt.imagefinder.data.repository.ImageRepository
import com.gunt.imagefinder.data.repository.network.response.ResponseKakao
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.reactivex.rxjava3.core.Flowable

@Module
@InstallIn(ActivityRetainedComponent::class)
class ImageRepositoryRemote
constructor(
    private var imageDocumentService: ImageDocumentService,
) : ImageRepository {

    override fun findImage(title: String, page: Int): Flowable<ResponseKakao> {
        return imageDocumentService.requestImageDocs(title = title, page = page)
    }
}
