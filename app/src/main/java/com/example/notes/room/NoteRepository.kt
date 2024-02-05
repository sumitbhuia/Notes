package com.example.notes.room

import androidx.lifecycle.LiveData

class NoteRepository(private val db:NoteDatabase) {
    suspend fun insertNote(note:Note){
        return db.getNoteDAO().insertNote(note)
    }

    suspend fun updateNote(note:Note){
        return db.getNoteDAO().updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        return db.getNoteDAO().deleteNote(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return db.getNoteDAO().getAllNotes()
    }

    fun searchNote(query:String?): LiveData<List<Note>> {
        return db.getNoteDAO().searchNote(query)
    }

}