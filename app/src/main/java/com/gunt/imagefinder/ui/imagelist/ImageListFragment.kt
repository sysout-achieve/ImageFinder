package com.gunt.imagefinder.ui.imagelist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.gunt.imagefinder.BR
import com.gunt.imagefinder.R
import com.gunt.imagefinder.databinding.FragmentImageListBinding
import com.gunt.imagefinder.util.EndlessRecyclerScrollListener
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

const val GRID_SPAN_COUNT = 2
const val REQUEST_ENDLESS_CNT: Int = 10

@AndroidEntryPoint
class ImageListSearchFragment : Fragment() {

    private val viewModel: ImageListViewModel by viewModels()

    private lateinit var binding: FragmentImageListBinding
    private lateinit var listAdapter: ImageListAdapter
    private lateinit var dialogBuilder: AlertDialog.Builder

    private var compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_image_list, container, false)
        binding.setVariable(BR.imageListViewModel, viewModel)
        val view = binding.root
        binding.lifecycleOwner = this

        setupSearchEditTextChangeListener()
        setupListAdapter()
        setupEndScrollListener()
        setupSwipeRefreshListener()
        setupLoadingObserver()
        setupFilterObserver()

        binding.executePendingBindings()
        dialogBuilder = AlertDialog.Builder(binding.root.context)

        binding.btnCategory.setOnClickListener { showAlert() }

        return view
    }

    // 자동 검색 기능
    private fun setupSearchEditTextChangeListener() {
        val subscription: Disposable =
            binding.editSearch.textChanges().throttleWithTimeout(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onNext = {
                        viewModel.onTriggerEvent(ImageListEventType.NewSearch)
                    }
                )
        compositeDisposable.add(subscription)
    }

    // viewModel의 filterList Observe
    private fun setupFilterObserver() {
        viewModel.filter.observe(
            this.viewLifecycleOwner,
            Observer {
                val temp = it.toTypedArray<CharSequence>()
                dialogBuilder.setTitle(getString(R.string.select_category))
                    .setItems(temp) { _, position ->
                        binding.btnCategory.text = temp[position]
                        viewModel.onTriggerEvent(ImageListEventType.NewFilter)
                    }
            }
        )
    }

    // viewModel의 Loading Observe(viewModelScope의 데이터 처리 완료 시 false 반환)
    private fun setupLoadingObserver() {
        viewModel.loading.observe(
            this.viewLifecycleOwner,
            Observer {
                binding.layoutSwipeRefresh.isRefreshing = it
            }
        )
    }

    private fun setupListAdapter() {
        listAdapter = ImageListAdapter()
        binding.imageList.layoutManager = GridLayoutManager(binding.root.context, GRID_SPAN_COUNT)
        binding.imageList.adapter = listAdapter
    }

    private fun setupEndScrollListener() {
        binding.imageList.addOnScrollListener(object : EndlessRecyclerScrollListener(REQUEST_ENDLESS_CNT) {
            override fun onLoadMore() {
                viewModel.onTriggerEvent(ImageListEventType.NextPage)
            }
        })
    }

    private fun setupSwipeRefreshListener() {
        binding.layoutSwipeRefresh.setOnRefreshListener {
            viewModel.onTriggerEvent(ImageListEventType.NewSearch)
        }
    }

    private fun showAlert() {
        dialogBuilder.create().show()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
