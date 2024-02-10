package com.example.notes.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


// Parcelable is an interface that a class can implement to be passed within an Intent from
// an Activity to another one, this way, transporting data from one Activity to another one.
//Good practice

@Entity(tableName = "notes")
@Parcelize
data class Note(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id:Int,

    @ColumnInfo
    val noteTitle:String,

    @ColumnInfo
    val noteBody:String

):Parcelable
