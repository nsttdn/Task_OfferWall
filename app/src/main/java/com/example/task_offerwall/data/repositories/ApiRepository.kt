package com.example.task_offerwall.data.repositories

import com.example.task_offerwall.data.models.ObjectResponse
import com.example.task_offerwall.data.models.ApiEntity
import com.example.task_offerwall.network.ApiService

class ApiRepository(private val apiService: ApiService) {

    suspend fun getAllIds(): List<ApiEntity> {
        return apiService.getAllIds().data
    }

    suspend fun getObjectById(id: String): ObjectResponse {
        return apiService.getObjectById(id)
    }
}

