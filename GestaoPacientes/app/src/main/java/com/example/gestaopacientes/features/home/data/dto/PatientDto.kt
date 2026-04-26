package com.example.gestaopacientes.features.home.data.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.gestaopacientes.features.home.domain.Patient
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class PatientDto (
    val id: Int,
    val nome: String,
    val email: String,
    val telefone: String,
    val dataNascimento: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun PatientDto.toDomain() = Patient(
    id = id,
    nome = nome,
    idade = calcularIdade(dataNascimento)
)

@RequiresApi(Build.VERSION_CODES.O)
fun calcularIdade(data: String): Int {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val birthDate = LocalDate.parse(data, formatter)
    return Period.between(birthDate, LocalDate.now()).years
}
