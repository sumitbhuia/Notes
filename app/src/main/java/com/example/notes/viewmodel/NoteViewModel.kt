package com.example.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.room.Note
import com.example.notes.room.NoteRepository
import kotlinx.coroutines.launch

// View Model helps in persistence of data
// All that functions and va=variable that need data persistence are here
//As they are suspend function so need to be accessed by viewModelScope from repository

class NoteViewModel(val app:Application , private val noteRepository: NoteRepository) : AndroidViewModel(app) {

    fun addNote(note:Note) = viewModelScope.launch { noteRepository.insertNote(note) }
    fun updateNote(note:Note) = viewModelScope.launch { noteRepository.updateNote(note) }
    fun deleteNote(note:Note) = viewModelScope.launch { noteRepository.deleteNote(note) }

    fun getAllNotes() = noteRepository.getAllNotes()
    fun searchNote(query: String?) = noteRepository.searchNote(query)




}