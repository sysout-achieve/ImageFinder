package com.gunt.imagefinder.imagelist

import com.google.common.truth.Truth
import com.gunt.imagefinder.data.repository.network.ImageRepositoryRemote
import com.gunt.imagefinder.data.repository.network.REQUEST_IMAGE_LIST_SIZE_DEFAULT
import com.gunt.imagefinder.data.repository.network.model.ImageDocumentDtoMapper
import com.gunt.imagefinder.di.RetrofitModule
import com.gunt.imagefinder.ui.imagelist.ImageListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ImageListViewModelConnectionTest {

    lateinit var imageListViewModel: ImageListViewModel

    @Before
    fun setUp() {
        imageListViewModel = ImageListViewModel(
            ImageRepositoryRemote(
                RetrofitModule.provideRetrofitApiService(),
            )
        )
    }

    @Test
    fun searchImgListWithApiTest() = runBlocking {
        // given
        imageListViewModel.search.searchStr = "혜리"
        val expected = REQUEST_IMAGE_LIST_SIZE_DEFAULT

        // when
        val responseKakao = imageListViewModel.getResponseFromRepository()
//        val list = responseKakao.documents

        // then
//        print(list[0])
//        Truth.assertThat(list.size).isEqualTo(expected)
    }

    @Test
    fun name() {
        val temp = mutableSetOf("a")
        val list = mutableListOf("a", "b", "c")
        list.filter { (temp.add(it)||true) || it =="b" }
        print(temp.add("b"))
        print(temp.toString())


    }
}
