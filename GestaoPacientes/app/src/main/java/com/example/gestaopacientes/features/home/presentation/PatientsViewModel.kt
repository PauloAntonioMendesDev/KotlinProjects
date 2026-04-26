package com.example.gestaopacientes.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestaopacientes.features.home.domain.GetPatientsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PatientsViewModel(private val useCase: GetPatientsUseCase) : ViewModel() {
    private val _state = MutableStateFlow<PatientsState>(PatientsState.Loading)
    val state: StateFlow<PatientsState> = _state

    fun loadPatients(){
        viewModelScope.launch {
            _state.value = PatientsState.Loading
            runCatching {
                useCase.execute()
            }.onSuccess {
                _state.value = PatientsState.Success(it)
            }.onFailure {
                _state.value = PatientsState.Error(it.message ?: "Erro ao buscar pacientes")
            }
        }
    }

}