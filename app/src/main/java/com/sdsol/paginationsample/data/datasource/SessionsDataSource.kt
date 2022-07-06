package com.sdsol.paginationsample.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sdsol.paginationsample.model.request.provider_sessions.GetProviderSessionsRequest
import com.sdsol.paginationsample.model.response.provider_sessions.Listing
import com.sdsol.paginationsample.network.BackEndApi

class SessionsDataSource(val request: GetProviderSessionsRequest, val retrofitClient: BackEndApi) :
    PagingSource<Int, Listing>() {
    override fun getRefreshKey(state: PagingState<Int, Listing>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Listing> {
        try {
            var nextPageNumber: Int? = null
            val currentLoadingPageKey = params.key ?: 1
            request.PageIndex = currentLoadingPageKey
            val response = retrofitClient.getProviderSessionsList(request)
            val totalPages = response.response?.dataObject?.sessionsList?.totalPages
            val newList = response.response?.let {
                Log.d("Current_Page", "$currentLoadingPageKey ${response.response?.dataObject?.sessionsList?.totalPages}")
                it.dataObject?.sessionsList?.listing ?: mutableListOf()
            } ?: run {
                mutableListOf()
            }

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            nextPageNumber = if (totalPages!! > currentLoadingPageKey) {
                currentLoadingPageKey + 1
            } else {
                null
            }

            return LoadResult.Page(
                data = newList,
                prevKey = prevKey,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }
}