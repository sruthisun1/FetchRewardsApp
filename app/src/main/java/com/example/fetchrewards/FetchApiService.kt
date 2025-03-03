package com.example.fetchrewards.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Service interface for Fetch Rewards API
interface FetchApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>

    companion object {
        private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

        //Creates an instance of `FetchApiService` using Retrofit
        fun create(): FetchApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FetchApiService::class.java)
        }
    }
}