package com.sdsol.paginationsample.model.response.provider_sessions


import com.google.gson.annotations.SerializedName

data class ProviderSessionResponse(
    @SerializedName("SessionsList")
    var sessionsList: SessionsList
)