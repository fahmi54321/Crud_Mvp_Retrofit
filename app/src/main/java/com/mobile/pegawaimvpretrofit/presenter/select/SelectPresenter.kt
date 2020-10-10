package com.mobile.pegawaimvpretrofit.presenter.select

import com.mobile.pegawai.model.ResponseUser
import com.mobile.pendataransdmvprxandroid.network.kotlin.ConfigNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//todo 4 (select) construct interface
class SelectPresenter(var selectView: SelectView) {

    //todo 2 (select) membuat fungsi select
    fun select(){

        //todo 3 (select) konfigurai retrofit
        ConfigNetwork.getRetrofit().selectUser().enqueue(object : Callback<ResponseUser>{
            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                selectView.onError(t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful){
                    var data = response.body()?.result
                    if (data?.size?:0>0){
                        selectView.onSuccess(response.message(),data)
                    }else{
                        selectView.onError(response.message())
                    }
                }else{
                    selectView.onError(response.message())
                }
            }

        })
    }
}