package com.gunt.imagefinder.di

import com.gunt.imagefinder.data.repository.ImageRepository
import com.gunt.imagefinder.data.repository.network.ImageDocumentService
import com.gunt.imagefinder.data.repository.network.ImageRepositoryRemote
import com.gunt.imagefinder.data.repository.network.model.ImageDocumentDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideImageRepository(
        imageDocumentService: ImageDocumentService,
        imageDocumentDtoMapper: ImageDocumentDtoMapper
    ): ImageRepository {
        return ImageRepositoryRemote(
            imageDocumentService = imageDocumentService,
            mapper = imageDocumentDtoMapper
        )
    }
}
