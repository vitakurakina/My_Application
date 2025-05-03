package com.example.myapplication

import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private val notes: MutableList<Note>,
    private val onNoteClick: (Note, Int) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view, onNoteClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position], position)
    }

    override fun getItemCount(): Int = notes.size

    fun updateNote(position: Int, updatedNote: Note) {
        notes[position] = updatedNote
        notifyItemChanged(position)
    }
    fun deleteNote(position: Int) {
        notifyItemRemoved(position)
    }

    class NoteViewHolder(
        itemView: View,
        private val onNoteClick: (Note, Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.note_title)
        private val contentTextView: TextView = itemView.findViewById(R.id.note_content)

        fun bind(note: Note, position: Int) {
            titleTextView.text = note.title
            contentTextView.text = note.content

            itemView.setOnClickListener {
                onNoteClick(note, position)
            }
        }
    }
}
