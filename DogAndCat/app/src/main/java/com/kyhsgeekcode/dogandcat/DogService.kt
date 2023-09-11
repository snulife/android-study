package com.kyhsgeekcode.dogandcat

import retrofit2.http.GET

interface DogApi {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): DogImageResponse
}

data class DogImageResponse(
    val message: String,
    val status: String
)