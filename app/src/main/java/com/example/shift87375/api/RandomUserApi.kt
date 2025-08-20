package com.example.shift87375.api

import com.example.shift87375.model.RandomUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {
    @GET("api/")
    suspend fun getUsers(
        @Query("results") results: Int = 26,
        @Query("inc") inc: String = "name,location,phone,login,picture,email,gender,dob"
    ): RandomUserResponse
}