package com.mobile.pegawaimvpretrofit.presenter.delete

import com.mobile.pegawai.model.ResponseUser
import com.mobile.pendataransdmvprxandroid.network.kotlin.ConfigNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//todo 4(delete) contruct interface
class DeletePresenter(var deleteView: DeleteView) {

    //todo 2(delete) membuat fungsi delete
    fun delete(idUser: String?){

        //todo 3(delete) membuat fungsi delete
        ConfigNetwork.getRetrofit().deleteUser(idUser).enqueue(object : Callback<ResponseUser>{
            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                deleteView.onErrorDelete(t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful){
                    deleteView.onSuccessDelete(response.message())
                }else{
                    deleteView.onErrorDelete(response.message())
                }
            }

        })
    }
}