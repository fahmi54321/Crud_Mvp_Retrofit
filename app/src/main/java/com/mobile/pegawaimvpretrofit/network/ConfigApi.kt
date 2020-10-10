package com.mobile.pendataransdmvprxandroid.network.kotlin

import com.mobile.pegawai.model.ResponseUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


//todo 2.1 Config API
interface ConfigApi {

    @GET("selectUser.php")
    fun selectUser(): Call<ResponseUser>
//
    @GET("deleteUser.php")
    fun deleteUser(
        @Query("id_user") id_user: String?
    ): Call<ResponseUser>
//
    @Multipart
    @POST("signup.php")
    fun signup(
        @Part("nama") nama:RequestBody,
        @Part("email") email:RequestBody,
        @Part("password") password:RequestBody,
        @Part photo:MultipartBody.Part
    ): Call<ResponseUser>
//
    @Multipart
    @POST("editPegawai.php")
    fun editPegawai(
        @Part("id_user") id_user:RequestBody,
        @Part("nama") nama:RequestBody,
        @Part("email") email:RequestBody,
        @Part("password") password:RequestBody,
        @Part photo:MultipartBody.Part
    ): Call<ResponseUser>
    
}