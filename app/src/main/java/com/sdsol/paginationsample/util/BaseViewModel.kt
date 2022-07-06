package com.sdsol.paginationsample.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sdsol.paginationsample.R
import com.sdsol.paginationsample.di.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    protected val _toast = MutableLiveData<Event<Any?>>()
    val toast: LiveData<Event<Any?>>
        get() = _toast


    protected val handler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is SocketTimeoutException -> {
                _toast.postValue(Event(R.string.slower_internet_connection))
            }
            is TimeoutException -> {
                _toast.postValue(Event(R.string.slower_internet_connection))
            }
            is HttpException -> {
                _toast.postValue(Event(R.string.unknown_error_occoured))
            }
            is UnknownHostException -> {
                _toast.postValue(Event(R.string.slower_internet_connection))
            }
            else -> {
                _toast.postValue(Event(exception.message ?: exception.toString()))
            }
        }
    }

    protected fun ResourceProvider.isInternetUnAvailable(): Boolean {
        val result =  !applicationContext.isInternetAvailable()
        if (result)
            _toast.postValue(Event(R.string.please_check_your_internet_connection))
        return result
    }
}