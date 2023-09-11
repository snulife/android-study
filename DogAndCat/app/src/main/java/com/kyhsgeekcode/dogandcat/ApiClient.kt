package com.kyhsgeekcode.dogandcat

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://catfact.ninja/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val catFactService: CatFactService = retrofit.create(CatFactService::class.java)

    private val dogRetrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val dogApi: DogApi = dogRetrofit.create(DogApi::class.java)
}