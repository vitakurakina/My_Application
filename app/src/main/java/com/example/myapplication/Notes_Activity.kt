package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.AlertDialog
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Notes_Activity : ComponentActivity() {

    private val notesList = mutableListOf<Note>()
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notes)

        // Обработчик оконных отступов
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_notes)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val osulogoButton = findViewById<ImageButton>(R.id.nav_osu_logo)
        val scheduleNavButton = findViewById<ImageButton>(R.id.nav_schedule)
        val floatingActionButton = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.floatingActionButton)

        // Настройка RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.notesRecyclerView)
        adapter = NotesAdapter(notesList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Навигация
        scheduleNavButton.setOnClickListener {
            val intent = Intent(this, Schedule_activity::class.java)
            startActivity(intent)
        }

        osulogoButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Обработчик кнопки для создания новой заметки
        floatingActionButton.setOnClickListener {
            showAddNoteDialog()
        }
    }

    // Диалог для создания новой заметки
    private fun showAddNoteDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_note, null)
        builder.setView(dialogView)

        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val contentEditText = dialogView.findViewById<EditText>(R.id.editTextContent)

        builder.setTitle("New Note")
        builder.setPositiveButton("Save") { _, _ ->
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                addNote(title, content)
            } else {
                Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel", null)

        builder.show()
    }

    // Логика добавления новой заметки
    private fun addNote(title: String, content: String) {
        val newNote = Note(notesList.size + 1, title, content)
        //notesList.add(newNote)
        adapter.addNote(newNote) // Добавление новой заметки в адаптер
        adapter.notifyItemInserted(notesList.size - 1) // Уведомление адаптера о добавлении нового элемента
        Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
    }
}
