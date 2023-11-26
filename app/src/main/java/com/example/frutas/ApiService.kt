package com.example.frutas

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface FruitApi {

        @GET("fruit/all")
        suspend fun getFruits(): Response<List<Fruit>>

        @GET
        suspend fun getJokeByCategory(@Url url : String): Response<Fruit>
}