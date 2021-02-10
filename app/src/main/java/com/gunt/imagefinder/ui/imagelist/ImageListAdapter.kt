package com.gunt.imagefinder.ui.imagelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gunt.imagefinder.data.domain.ImageDocument
import com.gunt.imagefinder.databinding.ImagedocsItemBinding

class ImageListAdapter :
    ListAdapter<ImageDocument, ImageListAdapter.ViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    class ViewHolder private constructor(private val binding: ImagedocsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageDocument) {
            binding.imageDocs = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ImagedocsItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class ImageDiffCallback : DiffUtil.ItemCallback<ImageDocument>() {
    override fun areItemsTheSame(oldItem: ImageDocument, newItem: ImageDocument): Boolean {
        return oldItem.image_url == newItem.image_url
    }

    override fun areContentsTheSame(oldItem: ImageDocument, newItem: ImageDocument): Boolean {
        return oldItem == newItem
    }
}
