package com.gunt.imagefinder.data.repository

import com.google.common.truth.Truth.assertThat
import com.gunt.imagefinder.data.domain.ImageDocument
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ImageRepositoryTest {

  lateinit var imageRepository: ImageRepository
  lateinit var imageList: MutableList<ImageDocument>

  @Before
  fun setUp() {
    imageList = mutableListOf(
      ImageDocument(
        collection = "blog",
        thumbnail_url = "https://search3.kakaocdn.net/argon/130x130_85_c/JC8LrBAhH4Q"
      ),
      ImageDocument(
        collection = "etc",
        thumbnail_url = "https://search2.kakaocdn.net/argon/130x130_85_c/ETvWnjL98F2"
      ),
      ImageDocument(
        collection = "etc",
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
    imageRepository = FakeImageRepository(imageList)
  }

  @Test
  fun searchImageListTest() = runBlocking {
    // given
    val expected = imageList.size

    // when
    val list = imageRepository.findImage("", 1)

    // then
    assertThat(list.documents).hasSize(expected)
  }
}
