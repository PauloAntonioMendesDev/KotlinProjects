package com.example.gestaopacientes.core.network

import com.example.gestaopacientes.core.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            sessionManager.getToken().first()
        }
        val request = chain.request().newBuilder()

        token?.let {
            println("Token: $it")
            request.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(request.build())
    }
}