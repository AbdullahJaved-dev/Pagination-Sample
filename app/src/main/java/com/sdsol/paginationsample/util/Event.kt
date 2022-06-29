package com.sdsol.paginationsample.util

/**
 * Event class to pass events from viewmodel
 */
open class Event<out T>(private val content: T) {

    /**
     * False when event is not updated
     */
    var hasBeenHandled = false
        private set

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}