package com.sdsol.paginationsample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.sdsol.paginationsample.databinding.LayoutSessionItemBinding
import com.sdsol.paginationsample.model.response.provider_sessions.Listing
import com.sdsol.paginationsample.util.DiffUtilCallback

class SessionAdapter : RecyclerView.Adapter<SessionAdapter.SessionViewHolder>() {

    private val sessionsList = mutableListOf<Listing>()

    inner class SessionViewHolder(val layoutSessionItemBinding: LayoutSessionItemBinding) :
        RecyclerView.ViewHolder(layoutSessionItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        return SessionViewHolder(
            LayoutSessionItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    fun setSessionList(list: MutableList<Listing>) {
        val diffUtilCallback = DiffUtilCallback(sessionsList, list)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
        sessionsList.clear()
        sessionsList.addAll(list)
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val sessionItem = sessionsList[holder.bindingAdapterPosition]
        holder.layoutSessionItemBinding.apply {
            tvServicedName.text = sessionItem.ClientName
            tvUserName.text = sessionItem.sessionDate
            ivUser.load(sessionItem.thumbnailURL) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun getItemCount(): Int = sessionsList.size
}