package com.example.gestaopacientes.features.home.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestaopacientes.R
import com.example.gestaopacientes.core.SessionManager
import com.example.gestaopacientes.features.home.data.repository.PatientsRepositoryImpl
import com.example.gestaopacientes.features.home.domain.GetPatientsUseCase
import com.example.gestaopacientes.features.patients.presentation.adapter.PatientsAdapter
import kotlinx.coroutines.launch

class HomeActivity: AppCompatActivity() {

    private lateinit var recyclerPatients: RecyclerView
    private lateinit var adapter: PatientsAdapter

    private lateinit var viewModel: PatientsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupDependencies()

        recyclerPatients = findViewById(R.id.recyclerPatients)

        adapter = PatientsAdapter {
            patient ->
            Toast.makeText(
                this,
                patient.nome,
                Toast.LENGTH_SHORT
            ).show()
        }

        recyclerPatients.layoutManager = LinearLayoutManager(this)

        recyclerPatients.adapter = adapter
    }

    private fun setupDependencies(){
        val sessionManager = SessionManager(this)
        val patientsApi = RetrofitClient.patientsApi(sessionManager);
        val repository = PatientsRepositoryImpl(patientsApi)
        val usecase = GetPatientsUseCase(repository)
        viewModel = PatientsViewModel(usecase)
        viewModel.loadPatients()
        observeState()
    }

    private fun observeState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.state.collect { state ->
                        when(state){
                            is PatientsState.Loading -> {}
                            is PatientsState.Success -> {
                                adapter.updateList(state.patients)
                            }
                            is PatientsState.Error ->{}
                    }
                }
            }
        }
    }

}