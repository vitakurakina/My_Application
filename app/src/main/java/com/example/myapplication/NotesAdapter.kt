package com.example.myapplication

import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private val notes: MutableList<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int = notes.size

    fun addNote(note: Note) {
        notes.add(note)
        notifyItemInserted(notes.size - 1) // Уведомить адаптер о добавлении нового элемента
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.note_title)
        private val contentTextView: TextView = itemView.findViewById(R.id.note_content)

        fun bind(note: Note) {
            titleTextView.text = note.title
            contentTextView.text = note.content
        }
    }
}
