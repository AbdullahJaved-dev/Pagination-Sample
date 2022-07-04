package com.sdsol.paginationsample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sdsol.paginationsample.di.ResourceProvider
import com.sdsol.paginationsample.model.request.provider_sessions.GetProviderSessionsRequest
import com.sdsol.paginationsample.model.response.provider_sessions.Listing
import com.sdsol.paginationsample.network.BackEndApi
import com.sdsol.paginationsample.util.BaseViewModel
import com.sdsol.paginationsample.util.Event
import com.sdsol.paginationsample.util.toMain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val retrofitClient: BackEndApi
) : BaseViewModel() {

    val sessionsLiveData = MutableLiveData<Event<MutableList<Listing>>>()

    fun getSessions(pageNo: Int) {
        if (resourceProvider.isInternetUnAvailable())
            return
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO + handler) {
            val getSessionsResponse = retrofitClient.getProviderSessionsList(
                GetProviderSessionsRequest(
                    "Past",
                    pageNo,
                    50,
                    65
                )
            ).response
            toMain {
                isLoading.postValue(false)
                getSessionsResponse?.apply {
                    if (code == 0) {
                        sessionsLiveData.postValue(Event(dataObject?.sessionsList?.listing!!))
                    } else {
                        _toast.postValue(Event(status))
                    }
                }
            }
        }
    }
}