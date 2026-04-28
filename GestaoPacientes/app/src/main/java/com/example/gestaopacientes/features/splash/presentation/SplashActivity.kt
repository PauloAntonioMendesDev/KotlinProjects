package com.example.gestaopacientes.features.splash.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gestaopacientes.R
import com.example.gestaopacientes.core.SessionManager
import com.example.gestaopacientes.features.home.presentation.HomeActivity
import com.example.gestaopacientes.features.login.presentation.LoginActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.launch

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        verifyLogin()
    }

      fun verifyLogin(){
        val sessionManager = SessionManager(this);
       lifecycleScope.launch {
                val token = sessionManager.getToken().first();
                if(token != null){
                        goToHome()
                   }else{
                       goToLogin()
                   }
       }
    }

    fun goToHome(){
        val intent = Intent(this, HomeActivity::class.java);
        startActivity(intent)
    }

    fun goToLogin(){
        val intent = Intent(this, LoginActivity::class.java);
        startActivity(intent)
    }
}