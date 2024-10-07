package com.example.task_offerwall.network

import retrofit2.http.GET
import retrofit2.http.Path
import com.example.task_offerwall.data.models.IdResponse
import com.example.task_offerwall.data.models.ObjectResponse

interface ApiService {

    @GET("/api/v1/entities/getAllIds")
    suspend fun getAllIds(): IdResponse

    @GET("/api/v1/object/{id}")
    suspend fun getObjectById(@Path("id") id: String): ObjectResponse
}
