package com.example.notes.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

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
