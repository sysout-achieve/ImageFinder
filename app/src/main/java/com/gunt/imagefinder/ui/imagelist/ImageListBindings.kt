package com.gunt.imagefinder.ui.imagelist

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gunt.imagefinder.data.domain.ImageDocument

@BindingAdapter("items")
fun setItems(recyclerView: RecyclerView, items: List<ImageDocument>?) {
    items?.let {
        (recyclerView.adapter as ImageListAdapter).submitList(items)
    }
}

@BindingAdapter("setAdapter")
fun setAdapter(recyclerView: RecyclerView, imageListAdapter: ImageListAdapter) {
    recyclerView.adapter = imageListAdapter
}