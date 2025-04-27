package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.ComponentActivity

//import com.example.myapplication.ui.theme.screens.MainScreen

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        val scheduleButton = findViewById<Button>(R.id.button)
        val notesButton = findViewById<Button>(R.id.button2)

        val scheduleNavButton = findViewById<ImageButton>(R.id.nav_schedule)
        val notesNavButton = findViewById<ImageButton>(R.id.nav_notes)

        scheduleButton.setOnClickListener {
            val intent = Intent(this, Schedule_activity::class.java)
            startActivity(intent)
        }
        notesButton.setOnClickListener {
            val intent = Intent(this, Notes_Activity::class.java)
            startActivity(intent)
        }

        scheduleNavButton.setOnClickListener {
            val intent = Intent(this, Schedule_activity::class.java)
            startActivity(intent)
        }

        notesNavButton.setOnClickListener {
            val intent = Intent(this, Notes_Activity::class.java)
            startActivity(intent)
        }


    }
}