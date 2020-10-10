package com.mobile.pegawaimvpretrofit.presenter.select

import com.mobile.pegawai.model.ResultItem

//todo 1 (select) kerangka respon
interface SelectView {
    fun onSuccess(message:String,result: List<ResultItem?>?)
    fun onError(message:String)
}