package com.sdsol.paginationsample.ui

import android.util.Log
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val retrofitClient: BackEndApi
) : BaseViewModel() {

    var sessionsFlow: Flow<PagingData<Listing>> = flowOf(PagingData.empty())

    private fun getSessions(): Flow<PagingData<Listing>> {
        if (resourceProvider.isInternetUnAvailable())
            return flowOf(PagingData.empty())
        val request = GetProviderSessionsRequest(
            "Past",
            1,
            10,
            65
        )
        sessionsFlow = Pager(PagingConfig(10, initialLoadSize = 2)) {
            SessionsDataSource(request, retrofitClient)
        }.flow.cachedIn(viewModelScope)
        return sessionsFlow
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(MainActivityViewModel::class.qualifiedName, "onCleared: c")
    }
    init {
        getSessions()
    }
}