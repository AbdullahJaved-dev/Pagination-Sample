package com.sdsol.paginationsample.network

import com.google.gson.annotations.SerializedName


class WebResponse<T> {
    @SerializedName("Response")
    var response: Response<T>? = null
}