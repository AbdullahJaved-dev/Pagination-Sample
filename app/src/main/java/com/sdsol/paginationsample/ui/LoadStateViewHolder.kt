package com.sdsol.paginationsample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.sdsol.paginationsample.R
import com.sdsol.paginationsample.databinding.LoadingFooterBinding

class LoadStateViewHolder(private val binding: LoadingFooterBinding, retry: () -> Unit) :
RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text =
              itemView.context.getString(R.string.error_text_label)
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.loading_footer, parent, false)
            val binding = LoadingFooterBinding.bind(view)
            return LoadStateViewHolder(binding, retry)
        }
    }

}
