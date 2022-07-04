package com.sdsol.paginationsample.network

import com.sdsol.paginationsample.model.request.provider_sessions.GetProviderSessionsRequest
import com.sdsol.paginationsample.model.response.provider_sessions.ProviderSessionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BackEndApi {
    @POST("Provider/GetProviderSessionsList")
    suspend fun getProviderSessionsList(@Body getProviderSessionsRequest: GetProviderSessionsRequest): WebResponse<ProviderSessionResponse>
}