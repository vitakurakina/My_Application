package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        val scheduleButton = findViewById<Button>(R.id.button)
        val notesButton = findViewById<Button>(R.id.button2)
        val chatButton = findViewById<Button>(R.id.button3)
        val greetingTextView = findViewById<TextView>(R.id.textView)

        loadUserLogin { login ->
            if (login != null) {
                greetingTextView.text = "Привет, $login!"
            } else {
                greetingTextView.text = "Привет, студент!"
            }
        }

        scheduleButton.setOnClickListener {
            val intent = Intent(this, Schedule_activity::class.java)
            startActivity(intent)
        }
        notesButton.setOnClickListener {
            val intent = Intent(this, Notes_Activity::class.java)
            startActivity(intent)
        }
        chatButton.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadUserLogin(callback: (String?) -> Unit) {
        val userId = auth.currentUser?.uid ?: run {
            callback(null)
            return
        }

        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                val login = document.getString("login")
                callback(login)
            }
            .addOnFailureListener {
                callback(null)
            }
    }
}