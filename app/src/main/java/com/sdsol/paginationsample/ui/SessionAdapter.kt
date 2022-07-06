package com.sdsol.paginationsample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.sdsol.paginationsample.databinding.LayoutSessionItemBinding
import com.sdsol.paginationsample.model.response.provider_sessions.Listing
import com.sdsol.paginationsample.util.DiffUtilCallback

class SessionAdapter : PagingDataAdapter<Listing, SessionAdapter.SessionViewHolder>(DiffUtilCallback()) {

    inner class SessionViewHolder(val layoutSessionItemBinding: LayoutSessionItemBinding) :
        RecyclerView.ViewHolder(layoutSessionItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        return SessionViewHolder(
            LayoutSessionItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val sessionItem = getItem(holder.bindingAdapterPosition)
        holder.layoutSessionItemBinding.apply {
            tvServicedName.text = sessionItem?.sessionDate
            tvUserName.text = sessionItem?.ClientName
            ivUser.load(sessionItem?.thumbnailURL) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }
}