package com.example.taskmanager_balanandreimariancirlanalexandrudamian

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if(validate(email, password)) {
                loginUser(email, password)
            }
        }
    }

    private fun validate(email: String, password: String): Boolean {
        if(email.isEmpty()) { etEmail.error = "Email required"; return false }
        if(password.isEmpty()) { etPassword.error = "Password required"; return false }
        return true
    }

    private fun loginUser(email: String, password: String) {
        Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
