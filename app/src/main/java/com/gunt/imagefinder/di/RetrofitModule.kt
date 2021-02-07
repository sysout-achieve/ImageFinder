package com.gunt.imagefinder.di

import com.gunt.imagefinder.BuildConfig
import com.gunt.imagefinder.data.repository.network.ImageDocumentService
import com.gunt.imagefinder.data.repository.network.model.ImageDocumentDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://dapi.kakao.com"

const val KAKAO_API_KEY = BuildConfig.KAKAO_KEY

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideImageDocumentDtoMapper(): ImageDocumentDtoMapper {
        return ImageDocumentDtoMapper()
    }

    @Singleton
    @Provides
    fun provideRetrofitApiService(): ImageDocumentService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageDocumentService::class.java)
    }
}
