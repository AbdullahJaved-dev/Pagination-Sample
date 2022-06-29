package com.sdsol.paginationsample.model.response.provider_sessions


import com.google.gson.annotations.SerializedName

data class SessionsList(
    @SerializedName("Listing")
    var listing: MutableList<Listing>,
    @SerializedName("PageNo")
    var pageNo: Int,
    @SerializedName("TotalPages")
    var totalPages: Int,
    @SerializedName("TotalRec")
    var totalRec: Int
)