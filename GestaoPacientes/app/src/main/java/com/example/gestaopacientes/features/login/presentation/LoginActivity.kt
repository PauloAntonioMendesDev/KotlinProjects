package com.example.gestaopacientes.features.login.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.gestaopacientes.R
import com.example.gestaopacientes.core.SessionManager
import com.example.gestaopacientes.core.network.RetrofitClient
import com.example.gestaopacientes.features.home.presentation.HomeActivity
import com.example.gestaopacientes.features.login.data.remote.AuthApi
import com.example.gestaopacientes.features.login.data.repository.AuthRepository
import com.example.gestaopacientes.features.login.data.repository.AuthRepositoryImpl
import com.example.gestaopacientes.features.login.domain.LoginUseCase
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginActivity: AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login)
        setupDependencies();
        setupListeners()

    }
    private fun setupDependencies(){
        val authApi = RetrofitClient.api;
        val repository = AuthRepositoryImpl(authApi);
        val useCase = LoginUseCase(repository);
        val sessionManager = SessionManager(this)
        viewModel = LoginViewModel(useCase, sessionManager);
    }

    private fun setupListeners(){
        val btnLogin = findViewById<MaterialButton>(R.id.btnLogin)
        val edtEmail = findViewById<TextInputEditText>(R.id.edtEmail)
        val edtPassword = findViewById<TextInputEditText>(R.id.edtPassword)

        btnLogin.setOnClickListener {
            viewModel.login(edtEmail.text.toString(), edtPassword.text.toString())
        }

        observeState(btnLogin, this)
    }

    private fun observeState(button: MaterialButton,  context: Context){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect { state ->
                    when(state){
                        is LoginState.Loading -> {
                            button.text = "Entrando..."
                            button.isEnabled = false
                        }
                        is LoginState.Success -> {
                            println("Logado com sucesso!")
                            Toast.makeText(this@LoginActivity, "Login realizado com sucesso!",
                                Toast.LENGTH_SHORT).show()
                            val intent = Intent(context, HomeActivity::class.java)
                            startActivity(intent)
                        }
                        is LoginState.Error -> {
                            println("Erro no Login!")
                            button.text = "Entrar"
                            button.isEnabled = true
                            Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                 }
            }
        }
    }
}