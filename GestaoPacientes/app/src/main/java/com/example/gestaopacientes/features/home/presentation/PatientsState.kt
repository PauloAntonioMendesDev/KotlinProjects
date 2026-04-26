package com.example.gestaopacientes.features.home.presentation

import com.example.gestaopacientes.features.home.domain.Patient

sealed class PatientsState {
    object Loading : PatientsState()
    data class Success(val patients: List<Patient>) : PatientsState()
    data class Error(val message: String) : PatientsState()
}