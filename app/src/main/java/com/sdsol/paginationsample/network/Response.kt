package com.sdsol.paginationsample.network

import com.google.gson.annotations.SerializedName

class Response<T> {

    @SerializedName("Code")
    var code: Int = 0

    @SerializedName("Status")
    var status: String? = null

    @SerializedName("DataObject")
    var dataObject: T? = null
}