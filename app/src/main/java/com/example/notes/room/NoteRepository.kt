package com.example.notes.room

// Implementing properties of DAO ->database > repository
class NoteRepository(private val db:NoteDatabase) {
    suspend fun insertNote(note:Note)= db.getNoteDAO().insertNote(note)
    suspend fun updateNote(note:Note) = db.getNoteDAO().updateNote(note)
    suspend fun deleteNote(note: Note) = db.getNoteDAO().deleteNote(note)
    fun getAllNotes() = db.getNoteDAO().getAllNotes()
    fun searchNote(query:String?) = db.getNoteDAO().searchNote(query)

}