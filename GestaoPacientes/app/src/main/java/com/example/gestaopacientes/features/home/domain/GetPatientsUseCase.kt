package com.example.gestaopacientes.features.home.domain

import com.example.gestaopacientes.features.home.data.repository.PatientsRepository

class GetPatientsUseCase(private val patientsRepository: PatientsRepository) {
    suspend fun execute(): List<Patient>{
        return patientsRepository.getPatients()
    }
}