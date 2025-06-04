package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class First_Page : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_page_layout)

        val LoginButton = findViewById<Button>(R.id.loginbutton)
        val RegisterButton = findViewById<Button>(R.id.registerbutton)

        LoginButton.setOnClickListener {
            val intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
        }
        RegisterButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}