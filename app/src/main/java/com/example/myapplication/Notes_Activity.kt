package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.AlertDialog
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Notes_Activity : ComponentActivity() {

    private val notesList = mutableListOf<Note>()
    private lateinit var adapter: NotesAdapter
    private val gson = Gson()
    private val sharedPrefName = "notes_pref"
    private val notesKey = "notes_list"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notes)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_notes)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val osulogoButton = findViewById<ImageButton>(R.id.nav_osu_logo)
        val scheduleNavButton = findViewById<ImageButton>(R.id.nav_schedule)
        val floatingActionButton = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.floatingActionButton)

        val recyclerView: RecyclerView = findViewById(R.id.notesRecyclerView)
        adapter = NotesAdapter(notesList) { note, position ->
            showEditNoteDialog(note, position)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Загружаем заметки из памяти
        loadNotes()

        scheduleNavButton.setOnClickListener {
            val intent = Intent(this, Schedule_activity::class.java)
            startActivity(intent)
        }

        osulogoButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        floatingActionButton.setOnClickListener {
            showAddNoteDialog()
        }
    }

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

    private fun addNote(title: String, content: String) {
        val newNote = Note(notesList.size + 1, title, content)
        notesList.add(newNote)
        adapter.notifyItemInserted(notesList.size - 1)
        saveNotes() // Сохраняем заметки после добавления
        Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
    }

    private fun saveNotes() {
        val sharedPref = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val json = gson.toJson(notesList)
        editor.putString(notesKey, json)
        editor.apply()
    }

    private fun loadNotes() {
        val sharedPref = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        val json = sharedPref.getString(notesKey, null)
        if (json != null) {
            val type = object : TypeToken<MutableList<Note>>() {}.type
            val savedNotes: MutableList<Note> = gson.fromJson(json, type)
            notesList.addAll(savedNotes)
            adapter.notifyDataSetChanged()
        }
    }
    private fun showEditNoteDialog(note: Note, position: Int) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_note, null)
        builder.setView(dialogView)

        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val contentEditText = dialogView.findViewById<EditText>(R.id.editTextContent)

        titleEditText.setText(note.title)
        contentEditText.setText(note.content)

        builder.setTitle("Edit Note")
        builder.setPositiveButton("Save") { _, _ ->
            val newTitle = titleEditText.text.toString()
            val newContent = contentEditText.text.toString()

            if (newTitle.isNotEmpty() && newContent.isNotEmpty()) {
                val updatedNote = Note(note.id, newTitle, newContent)
                notesList[position] = updatedNote
                adapter.updateNote(position, updatedNote)
                saveNotes()
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNeutralButton("Delete") { _, _ ->
            notesList.removeAt(position)
            adapter.deleteNote(position)
            saveNotes()
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }



}
