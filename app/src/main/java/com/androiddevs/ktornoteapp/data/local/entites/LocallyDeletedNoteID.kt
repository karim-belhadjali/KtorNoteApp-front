package com.androiddevs.ktornoteapp.data.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androiddevs.ktornoteapp.other.Constants.DELETED_NOTES_TABLE_NAME

@Entity(tableName = DELETED_NOTES_TABLE_NAME)
data class LocallyDeletedNoteID(
    @PrimaryKey(autoGenerate = false)
    val deletedNoteID: String
)
