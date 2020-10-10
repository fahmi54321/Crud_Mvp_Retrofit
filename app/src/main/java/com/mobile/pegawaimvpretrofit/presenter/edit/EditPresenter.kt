package com.mobile.pegawaimvpretrofit.presenter.edit

import com.mobile.pegawai.model.ResponseUser
import com.mobile.pendataransdmvprxandroid.network.kotlin.ConfigNetwork
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//todo 4(edit) construct interface
class EditPresenter(var editView: EditView) {

    //todo 2(edit) membuat fungsi edit
    fun edit(idUser:RequestBody,nama:RequestBody,email:RequestBody,password:RequestBody,photo:MultipartBody.Part){

        //todo 3(edit) konfigurasi retrofit
        ConfigNetwork.getRetrofit().editPegawai(idUser,nama,email,password,photo).enqueue(object : Callback<ResponseUser>{
            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                editView.onErrorEdit(t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful){
                    editView.onSuccessEdit(response.message())
                }else{
                    editView.onErrorEdit(response.message())
                }
            }

        })
    }
}