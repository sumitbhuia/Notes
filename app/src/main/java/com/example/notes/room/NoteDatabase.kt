package com.example.notes.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Note::class] , version = 1 , exportSchema = false)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun getNoteDAO() :NoteDAO

    //This is a singleton pattern approach to create a database , so that at a time only one database exists
    companion object{
        //The @volatile annotation in Kotlin is used to ensure that a variable is always read from main memory and not from a cache.
        // This is important in multithreaded applications, where multiple threads may be accessing the same variable.
        // Without the @volatile annotation, it is possible for one thread to read a stale value of the variable, which can lead to errors.
        @Volatile


        //In Kotlin, a question mark after a variable name indicates that the variable can hold a null value.
        // Null safety is a core feature of Kotlin and is designed to prevent NullPointerExceptions,
        // which are common errors in other programming languages.
        private var instance : NoteDatabase? = null


        //Returns true if array has at least one element.
        // Returns true if at least one element matches the given predicate.
        //Boolean
        private val LOCK = Any()


//        The context:Context parameter is a powerful tool that can be used to
//         access resources and services that are specific to the current context.
//        The Context class in Android is an abstract class that represents the current state of the application.
//        It provides access to various resources and services, such as the application's resources, databases, and shared preferences.
//        It also allows you to start activities,broadcast and receive intents, and access the application's package manager.
//
//        Function to initiate singleton pattern
        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:

            //Function to create database
            createDatabase(context).also{
                instance = it
            }
        }


        //Function create database
        private fun createDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,NoteDatabase::class.java,"note_db").build()

    }
}