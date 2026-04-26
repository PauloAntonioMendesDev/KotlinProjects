package com.example.gestaopacientes.features.home.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.gestaopacientes.features.home.data.dto.toDomain
import com.example.gestaopacientes.features.home.data.remote.PatientsApi
import com.example.gestaopacientes.features.home.domain.Patient

class PatientsRepositoryImpl(private val api: PatientsApi): PatientsRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getPatients(): List<Patient> {

        val response = api.getPactients()
        if(!response.isSuccessful){
            throw Exception("Erro ao buscar pacientes")
        }

        val body = response.body() ?: throw Exception("Resposta vazia")
        if(!body.success){
            throw Exception(body.message)
        }
        return body.data.map { it.toDomain() }
    }

}