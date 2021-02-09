package com.gunt.imagefinder.imagelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.gunt.imagefinder.data.domain.ImageDocument
import com.gunt.imagefinder.data.repository.FakeImageRepository
import com.gunt.imagefinder.data.repository.network.response.ResponseKakao
import com.gunt.imagefinder.data.repository.network.response.ResponseMeta
import com.gunt.imagefinder.ui.imagelist.ImageListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.reflect.Field
import java.lang.reflect.Method

@ExperimentalCoroutinesApi
class ImageListViewModelTest {

    private val total_count = 6
    private val is_end = false
    private val pageableCount = 2

    // 기본값(all) + 임의로 만든 리스트(imageList)의 collection 갯수 : (1 + 3)
    private val expectedCollectionCnt = 4

    lateinit var imageListViewModel: ImageListViewModel
    lateinit var responseKakao: ResponseKakao<ImageDocument>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val imageList = mutableListOf(
            ImageDocument(
                collection = "blog",
                thumbnail_url = "https://search3.kakaocdn.net/argon/130x130_85_c/JC8LrBAhH4Q"
            ),
            ImageDocument(
                collection = "news",
                thumbnail_url = "https://search2.kakaocdn.net/argon/130x130_85_c/ETvWnjL98F2"
            ),
            ImageDocument(
                collection = "news",
                thumbnail_url = "https://search1.kakaocdn.net/argon/130x130_85_c/IXJezql9ZEV"
            ),
            ImageDocument(
                collection = "cafe",
                thumbnail_url = "https://search2.kakaocdn.net/argon/130x130_85_c/IReAcFKrzyX"
            ),
            ImageDocument(
                collection = "cafe",
                thumbnail_url = "https://search1.kakaocdn.net/argon/130x130_85_c/5yJCVPPDnpF"
            ),
            ImageDocument(
                collection = "cafe",
                thumbnail_url = "https://search4.kakaocdn.net/argon/130x130_85_c/DJiIXGi0rWH"
            )
        )
        val responseMeta =
            ResponseMeta(total_count = total_count, is_end = is_end, pageableCount = pageableCount)

        responseKakao = ResponseKakao(responseMeta, imageList)

        imageListViewModel = ImageListViewModel(FakeImageRepository(imageList))
    }

    @Test
    fun initViewModelTest() {
        assertThat(imageListViewModel.search.searchStr).isEqualTo("")
        assertThat(imageListViewModel.search.page).isEqualTo(1)

        val field: Field =
            imageListViewModel.javaClass.getDeclaredField("imgItemsAll")
        field.isAccessible = true
        val responseImgItemsAll: List<ImageDocument> =
            field.get(imageListViewModel) as List<ImageDocument>
        assertThat(responseImgItemsAll).hasSize(0)
    }

    @Test
    fun newSearchEventTest() = runBlockingTest {
        // given
        imageListViewModel.imgItemsAll = listOf(
            ImageDocument(
                collection = "news",
                thumbnail_url = "https://search2.kakaocdn.net/argon/130x130_85_c/ETvWnjL98F2"
            )
        )
        val givenCnt = imageListViewModel.imgItemsAll.size
        imageListViewModel.search.searchStr = "혜리"

        // when
        imageListViewModel.newSearchEvent()
        val repositorySize = imageListViewModel.getResponseFromRepository().documents.size

        // then
        assertThat(imageListViewModel.search.page).isEqualTo(1)
        assertThat(imageListViewModel.imgItemsAll.size).isNotEqualTo(givenCnt + repositorySize)
        assertThat(imageListViewModel.imgItemsAll.size).isEqualTo(repositorySize)
    }

    @Test
    fun nextPageEventTest() = runBlockingTest {
        // given
        imageListViewModel.imgItemsAll = listOf(
            ImageDocument(
                collection = "news",
                thumbnail_url = "https://search2.kakaocdn.net/argon/130x130_85_c/ETvWnjL98F2"
            )
        )
        val givenPage = imageListViewModel.search.page
        val givenCnt = imageListViewModel.imgItemsAll.size
        imageListViewModel.search.searchStr = "혜리"
        val repositorySize = imageListViewModel.getResponseFromRepository().documents.size

        // when
        imageListViewModel.nextPageEvent()

        // then
        assertThat(imageListViewModel.search.page).isEqualTo(givenPage + 1)
        assertThat(imageListViewModel.imgItemsAll.size).isEqualTo(givenCnt + repositorySize)
        assertThat(imageListViewModel.imgItemsAll.size).isNotEqualTo(repositorySize)
    }

    @Test
    fun arrangeResponseTest() {
        // given
        val method: Method = imageListViewModel.javaClass.getDeclaredMethod(
            "arrangeResponse",
            ResponseKakao::class.java
        )
        method.isAccessible = true

        // when
        method.invoke(imageListViewModel, responseKakao)

        // then
        assertThat(imageListViewModel.search.isNextPageAvailable()).isFalse()
    }

    @Test
    fun getFilterListTest() {
        // given
        val field: Field =
            imageListViewModel.javaClass.getDeclaredField("_filter")
        field.isAccessible = true
        val _filter: MutableSet<String> = field.get(imageListViewModel) as MutableSet<String>
        assertThat(_filter).hasSize(1) // filter 기본값 all = 1

        val method: Method =
            imageListViewModel.javaClass.getDeclaredMethod(
                "getFilterListFromResponse",
                ResponseKakao::class.java
            )
        method.isAccessible = true

        // when
        method.invoke(imageListViewModel, responseKakao)

        // then
        assertThat(_filter.size).isEqualTo(expectedCollectionCnt)
    }

    @Test
    fun appendImageListTest() {
        // given
        imageListViewModel.imgItemsAll = listOf(
            ImageDocument(
                collection = "news",
                thumbnail_url = "https://search2.kakaocdn.net/argon/130x130_85_c/ETvWnjL98F2"
            )
        )
        val initSize = imageListViewModel.imgItemsAll.size
        val method: Method =
            imageListViewModel.javaClass.getDeclaredMethod("appendImageList", List::class.java)
        method.isAccessible = true
        val appendTargetList = responseKakao.documents

        // when
        method.invoke(imageListViewModel, appendTargetList)

        // then
        assertThat(imageListViewModel.imgItemsAll).hasSize(initSize + appendTargetList.size)
    }

    @Test
    fun revertInitialFilterTest() {
        // given
        val field: Field =
            imageListViewModel.javaClass.getDeclaredField("_filter")
        field.isAccessible = true
        val _filter: MutableSet<String> = field.get(imageListViewModel) as MutableSet<String>

        val method: Method =
            imageListViewModel.javaClass.getDeclaredMethod("revertInitialFilter")
        method.isAccessible = true

        // when
        method.invoke(imageListViewModel)

        // then
        assertThat(imageListViewModel.filter.value?.size).isEqualTo(0)
        assertThat(_filter.size).isEqualTo(1)
    }

    @Test
    fun arrangeFilterListTest() {
        // given
        val filterSet = mutableSetOf("news", "blog", "cafe")
        val method: Method =
            imageListViewModel.javaClass.getDeclaredMethod(
                "arrangeFilterList",
                MutableSet::class.java
            )
        method.isAccessible = true

        // when
        method.invoke(imageListViewModel, filterSet)

        // then
        assertThat(imageListViewModel.filter.value?.size).isEqualTo(3)
    }

    @Test
    fun loadFilteredItemsTest() = runBlockingTest {
        // given
        imageListViewModel.search.filterSelected = "cafe"
        val expected = responseKakao.documents
            .filter { it.collection == imageListViewModel.search.filterSelected }
            .size

        val method: Method =
            imageListViewModel.javaClass.getDeclaredMethod("loadFilteredItems", List::class.java)
        method.isAccessible = true

        // when
        method.invoke(imageListViewModel, responseKakao.documents)

        // then
        assertThat(imageListViewModel.imgItemsFiltered.value?.size).isEqualTo(expected)
    }
}
