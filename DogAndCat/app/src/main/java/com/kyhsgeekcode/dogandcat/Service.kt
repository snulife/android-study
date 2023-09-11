package com.kyhsgeekcode.dogandcat

import retrofit2.http.GET

interface CatFactService {
    @GET("fact")
    suspend fun getCatFact(): CatFact
}


data class CatFact(
    val fact: String,
    val length: Int
)