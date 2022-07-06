package com.sdsol.paginationsample.util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil.ItemCallback

class DiffUtilCallback<T : Any> : ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}