package com.androiddevs.ktornoteapp.data.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androiddevs.ktornoteapp.other.Constants.NOTE_TABLE_NAME
import com.google.gson.annotations.Expose
import java.util.*

@Entity(tableName = NOTE_TABLE_NAME)
data class Note(
    val title: String,
    val content: String,
    val date: Long,
    val owners: List<String>,
    val color: String,
    @Expose(deserialize = false, serialize = false)
    var isSynced: Boolean = false,
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString()
)