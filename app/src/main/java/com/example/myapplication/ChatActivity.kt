package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.FirebaseApp

class ChatActivity : ComponentActivity() {

    private val dbRef = FirebaseDatabase.getInstance().getReference("messages")
    private lateinit var messageAdapter: ChatAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activitychat)

        // Навигация
        findViewById<ImageButton>(R.id.nav_osu_logo).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        findViewById<ImageButton>(R.id.nav_notes).setOnClickListener {
            startActivity(Intent(this, Notes_Activity::class.java))
        }
        findViewById<ImageButton>(R.id.nav_schedule).setOnClickListener {
            startActivity(Intent(this, Schedule_activity::class.java))
        }

        // Настройка RecyclerView
        recyclerView = findViewById(R.id.messages_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        messageAdapter = ChatAdapter(emptyList()) // пустой список для начала
        recyclerView.adapter = messageAdapter

        // Слушатель сообщений
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newMessages = mutableListOf<Message>()
                for (data in snapshot.children) {
                    val msg = data.getValue(Message::class.java)
                    if (msg != null) newMessages.add(msg)
                }
                messageAdapter.updateMessages(newMessages)
                recyclerView.scrollToPosition(newMessages.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        // Отправка сообщений
        val sendButton = findViewById<Button>(R.id.send_button)
        val messageInput = findViewById<EditText>(R.id.message_input)

        sendButton.setOnClickListener {
            val messageText = messageInput.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val message = Message(text = messageText)
                dbRef.push().setValue(message)
                messageInput.setText("")
            }
        }
    }
}
