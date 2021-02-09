package com.gunt.imagefinder.ui.imagelist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gunt.imagefinder.data.domain.ImageDocument
import com.gunt.imagefinder.data.repository.ImageRepository
import com.gunt.imagefinder.data.repository.network.response.ResponseKakao
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class ImageListViewModel
@ViewModelInject
constructor(
    private var imageRepository: ImageRepository,
) : ViewModel() {

    var search = Search()
    var loading = MutableLiveData(false)
    var imgItemsAll: List<ImageDocument> = listOf()
    val imgItemsFiltered: MutableLiveData<List<ImageDocument>> = MutableLiveData(listOf())
    val imageListAdapter = ImageListAdapter()

    private var _filter: MutableSet<String> = mutableSetOf("all")
    var filter: MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())

    fun onTriggerEvent(event: ImageListEventType) {
        if (isLoading() == true) return
        loading.postValue(true)
        viewModelScope.launch {
            try {
                when (event) {
                    is ImageListEventType.NewSearch -> newSearchEvent()
                    is ImageListEventType.NextPage -> nextPageEvent()
                    is ImageListEventType.NewFilter -> newFilterEvent()
                }
            } catch (e: IllegalStateException) {
                getEmptyImageList()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                afterEvent()
            }
        }
    }

    suspend fun newSearchEvent() {
        revertInitialFilter()
        search.firstPage()
        isEmptySearchField()
        val response = getResponseFromRepository()
        imgItemsAll = getImageDocsList(response)
        arrangeResponse(response)
        filteringImageList(imgItemsAll)
    }

    suspend fun nextPageEvent() {
        if (!search.isNextPageAvailable()) {
            isEmptySearchField()
            search.incrementPage()
            val response = getResponseFromRepository()
            arrangeResponse(response)
            appendImageList(getImageDocsList(response))
            filteringImageList(imgItemsAll)
        }
    }

    private fun newFilterEvent() {
        filteringImageList(imgItemsAll)
    }

    private fun afterEvent() {
        arrangeFilterList(_filter)
        loading.postValue(false)
    }

    private fun getEmptyImageList() {
        imgItemsAll = listOf()
        filteringImageList(imgItemsAll)
    }

    private fun filteringImageList(allItems: List<ImageDocument>) {
        if (search.filterSelected == "all") loadAllItems(allItems)
        else loadFilteredItems(allItems)
    }

    private fun arrangeResponse(response: ResponseKakao<ImageDocument>) {
        search.setNextPageAvailable(response.meta.is_end)
        getFilterListFromResponse(response)
    }

    private fun getFilterListFromResponse(response: ResponseKakao<ImageDocument>) {
        getImageDocsList(response).forEach {
            _filter.add(it.collection)
        }
    }

    private fun appendImageList(list: List<ImageDocument>) {
        val current = ArrayList(imgItemsAll)
        current.addAll(list)
        imgItemsAll = current
    }

    private fun revertInitialFilter() {
        _filter.clear()
        _filter.add("all")
        filter.value?.clear()
    }

    private fun arrangeFilterList(filterSet: MutableSet<String>) {
        val tempList = arrayListOf<String>()
        filterSet.iterator().forEach {
            tempList.add(it)
        }
        filter.postValue(tempList)
    }

    private fun loadFilteredItems(allItems: List<ImageDocument>) {
        val itemListFiltered = allItems.filter { it.collection == search.filterSelected }
        imgItemsFiltered.postValue(itemListFiltered)
    }

    private fun loadAllItems(allItems: List<ImageDocument>) {
        imgItemsFiltered.postValue(allItems)
    }

    suspend fun getResponseFromRepository(): ResponseKakao<ImageDocument> {
        return imageRepository.findImage(title = search.searchStr, page = search.page)
    }

    private fun getImageDocsList(response: ResponseKakao<ImageDocument>): List<ImageDocument> {
        return response.documents
    }

    private fun isLoading(): Boolean? {
        return loading.value
    }

    private fun isEmptySearchField() {
        if (search.searchStr == "") throw IllegalStateException()
    }

    inner class Search {
        var searchStr = ""
        var page = 1
        private var isEnd = false
        var filterSelected = "all"

        fun incrementPage() {
            page++
        }

        fun firstPage() {
            page = 1
        }

        fun isNextPageAvailable(): Boolean {
            return this.isEnd
        }

        fun setNextPageAvailable(boolean: Boolean) {
            this.isEnd = boolean
        }
    }
}
