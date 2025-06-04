package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private lateinit var loginEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registrationButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = Firebase.auth

        loginEditText = findViewById(R.id.loginEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        registrationButton = findViewById(R.id.registrationButton)

        registrationButton.setOnClickListener {
            val login = loginEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (login.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            createUser(login, email, password)
        }
    }

    private fun createUser(login: String, email: String, password: String) {
        if (password.length < 6) {
            Toast.makeText(this, "Пароль должен быть не менее 6 символов", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveUserLogin(login)
                    sendEmailVerification()
                } else {
                    Toast.makeText(
                        this,
                        "Ошибка: ${task.exception?.message ?: "Unknown error"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun saveUserLogin(login: String) {
        val userId = auth.currentUser?.uid ?: return
        val user = hashMapOf(
            "login" to login,
            "email" to auth.currentUser?.email
        )

        db.collection("users")
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Логин сохранён", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Ошибка сохранения логина: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun sendEmailVerification() {
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                val msg = if (task.isSuccessful) {
                    "Письмо с подтверждением отправлено на ${auth.currentUser?.email}"
                } else {
                    "Ошибка отправки письма: ${task.exception?.message}"
                }
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }
    }
}