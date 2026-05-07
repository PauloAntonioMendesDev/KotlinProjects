package com.example.gestaopacientes.core.session

import android.content.Context
import android.content.Intent
import com.example.gestaopacientes.features.login.presentation.LoginActivity

class AppSessionExpiredHandler(private val context: Context): SessionExpiredHandler {
    override fun onSessionExpired() {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

}