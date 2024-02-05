package com.example.notes.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Note::class] , version = 1)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun getNoteDAO() :NoteDAO

    companion object{

        @Volatile
        private var instance : NoteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context:Context): NoteDatabase {
            val noteDatabase = instance ?: synchronized(LOCK) {
                instance ?: createDatabase(context).also { instance = it }
            }
            return noteDatabase
        }
        private
        fun createDatabase(context: Context) =Room.databaseBuilder(context.applicationContext,NoteDatabase::class.java,"note_db").build()

    }
}