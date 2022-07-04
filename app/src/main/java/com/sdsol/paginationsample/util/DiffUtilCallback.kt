package com.sdsol.paginationsample.util

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback<T>(private val oldList: MutableList<T>, private val newList: List<T>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldCourse: Int, newPosition: Int): Boolean {
        val a = oldList[oldCourse]
        val b = newList[newPosition]
        return a == b
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}