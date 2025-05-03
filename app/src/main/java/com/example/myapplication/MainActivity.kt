package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        val scheduleButton = findViewById<Button>(R.id.button)
        val notesButton = findViewById<Button>(R.id.button2)

        scheduleButton.setOnClickListener {
            val intent = Intent(this, Schedule_activity::class.java)
            startActivity(intent)
        }
        notesButton.setOnClickListener {
            val intent = Intent(this, Notes_Activity::class.java)
            startActivity(intent)
        }

    }
}