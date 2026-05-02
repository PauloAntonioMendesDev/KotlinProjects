package com.example.gestaopacientes.features.home.data.remote

import com.example.gestaopacientes.features.home.data.dto.PatientsResponse

import retrofit2.http.GET
import retrofit2.Response

interface PatientsApi {
    @GET("pacientes")
    suspend fun getPactients(): Response<PatientsResponse>
}