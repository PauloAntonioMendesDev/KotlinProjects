package com.example.gestaopacientes.features.login.presentation

import android.content.Context
import android.se.omapi.Session
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestaopacientes.core.SessionManager
import com.example.gestaopacientes.features.login.domain.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase, private val sessionManager: SessionManager): ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state


    fun login(email: String, password: String){
        viewModelScope.launch {
            _state.value = LoginState.Loading
            runCatching {
                loginUseCase.execute(email, password)
            }.onSuccess { token ->
                _state.value = LoginState.Success(token)
                sessionManager.saveToken(token)
            }.onFailure {
                _state.value = LoginState.Error(it.message ?: "Erro ao efetuar login")
            }
        }
    }
}