package com.androiddevs.ktornoteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.ktornoteapp.data.local.entites.LocallyDeletedNoteID
import com.androiddevs.ktornoteapp.data.local.entites.Note

@Database(
    entities = [Note::class,LocallyDeletedNoteID::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NotesDataBase : RoomDatabase(){

    abstract fun noteDao(): NoteDao
}