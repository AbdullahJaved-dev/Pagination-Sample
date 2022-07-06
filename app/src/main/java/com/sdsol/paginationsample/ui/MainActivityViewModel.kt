package com.sdsol.paginationsample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sdsol.paginationsample.data.datasource.SessionsDataSource
import com.sdsol.paginationsample.di.ResourceProvider
import com.sdsol.paginationsample.model.request.provider_sessions.GetProviderSessionsRequest
import com.sdsol.paginationsample.model.response.provider_sessions.Listing
import com.sdsol.paginationsample.network.BackEndApi
import com.sdsol.paginationsample.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val retrofitClient: BackEndApi
) : BaseViewModel() {

    var sessionsLiveData = MutableLiveData<PagingData<Listing>>()
    fun getSessions() {
        if (resourceProvider.isInternetUnAvailable())
            return
        val request = GetProviderSessionsRequest(
            "Past",
            1,
            10,
            65
        )
        val listData = Pager(PagingConfig(10)) {
            SessionsDataSource(request, retrofitClient)
        }.flow.cachedIn(viewModelScope)

        viewModelScope.launch {
            listData.collect {
                sessionsLiveData.postValue(it)
            }
        }

    }
}