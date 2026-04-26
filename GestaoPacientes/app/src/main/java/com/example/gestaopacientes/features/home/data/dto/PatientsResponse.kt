package com.example.gestaopacientes.features.home.data.dto

data class PatientsResponse (
    val success: Boolean,
    val message: String,
    val data: List<PatientDto>
)