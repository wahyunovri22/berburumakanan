package com.example.berburumakanan.network

import com.example.berburumakanan.model.ActionModel
import com.example.berburumakanan.model.ResponseResto
import com.semicolon.newsapp.helper.Config
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {

    @GET(Config.LOGIN)
    fun login(
        @Query("username") u:String,
        @Query("password") p:String,
    ): Call<ActionModel>

    @FormUrlEncoded
    @POST(Config.REGISTER)
    fun register(
        @Field("username") u:String,
        @Field("fullname") full:String,
        @Field("password") p:String,
    ): Call<ActionModel>

    @GET(Config.RESTO)
    fun resto(): Call<ResponseResto>

    @GET(Config.MENU)
    fun menu(
        @Query("id") u:String
    ): Call<ResponseResto>
}