package com.example.gestaopacientes.features.login.data.repository

import com.example.gestaopacientes.features.login.data.dto.ErrorResponse
import com.example.gestaopacientes.features.login.data.dto.LoginRequest
import com.example.gestaopacientes.features.login.data.remote.AuthApi
import com.google.gson.Gson
import retrofit2.HttpException

class AuthRepositoryImpl(private val authApi: AuthApi): AuthRepository {
    override suspend fun login(email: String, password: String): String {
        try{
            val request = LoginRequest(email, password)
            val response = authApi.login(request);
            if(response.isSuccessful){
                return response.body()?.data!!.token;
            }else{
                val errorBody = response.errorBody()?.string();
                val errorMessage = Gson().fromJson(errorBody, ErrorResponse::class.java).message
                throw Exception(errorMessage)
            }

        } catch (error: HttpException){
            println("Error: ${error.response()?.message()}")
            throw Exception(error.response()?.message() ?: "Erro ao efetuar login");
        }
    }
}