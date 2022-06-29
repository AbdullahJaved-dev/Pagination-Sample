package com.sdsol.paginationsample.model.request.provider_sessions

data class GetProviderSessionsRequest(
    var ListType: String,
    var PageIndex: Int,
    var PageSize: Int,
    var ProviderID: Int
)