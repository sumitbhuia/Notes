package com.example.notes.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDAO{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:Note)

    @Update
    suspend fun updateNote(note:Note)

    @Delete
    suspend fun deleteNote(note:Note)

    @Query("SELECT * FROM NOTES ORDER BY id DESC")
    fun getAllNotes() : LiveData<List<Note>>

//    @Query("SELECT * FROM NOTES WHERE noteTitle LIKE :query OR NOTEBODY LIKE :query")
//    fun searchNote(query:String?):LiveData<List<Note>>
@Query("SELECT * FROM NOTES WHERE LOWER(noteTitle) LIKE '%' || LOWER(:query) || '%' OR LOWER(NOTEBODY) LIKE '%' || LOWER(:query) || '%'")
fun searchNote(query: String?): LiveData<List<Note>>
}
