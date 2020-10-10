package com.mobile.pegawaimvpretrofit.presenter.register

import com.mobile.pegawai.model.ResponseUser
import com.mobile.pendataransdmvprxandroid.network.kotlin.ConfigNetwork
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//todo 5 (register) contruct interface
class RegisterPresenter(var registerView: RegisterView) {

    //todo 3 (register) membuat fungsi register
    fun register(nama:RequestBody,email:RequestBody,password:RequestBody,photo:MultipartBody.Part){

        //todo 4 (register) konfigurasi retrofit
        ConfigNetwork.getRetrofit().signup(nama,email,password,photo).enqueue(object : Callback<ResponseUser>{
            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                registerView.onError(t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful){
                    registerView.onSuccess(response.message())
                }else{
                    registerView.onError(response.message())
                }
            }

        })
    }
}