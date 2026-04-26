package com.example.gestaopacientes.features.home.data.repository

import com.example.gestaopacientes.features.home.domain.Patient

interface  PatientsRepository {
    suspend fun getPatients() : List<Patient>
}