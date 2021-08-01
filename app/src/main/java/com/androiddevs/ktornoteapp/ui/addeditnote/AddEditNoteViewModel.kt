package com.androiddevs.ktornoteapp.ui.addeditnote

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.ktornoteapp.data.local.entites.Note
import com.androiddevs.ktornoteapp.other.Constants.NOTE_NOT_FOUND
import com.androiddevs.ktornoteapp.other.Event
import com.androiddevs.ktornoteapp.other.Ressource
import com.androiddevs.ktornoteapp.repositories.NoteRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddEditNoteViewModel @ViewModelInject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _note = MutableLiveData<Event<Ressource<Note>>>()
    val note: LiveData<Event<Ressource<Note>>> = _note

    fun insert(note: Note) = GlobalScope.launch {
        repository.insertNote(note)
    }

    fun getNoteById(id: String) = viewModelScope.launch {
        _note.postValue(Event(Ressource.loading(null)))
        val note = repository.getNoteById(id)

        note?.let {
            _note.postValue(Event(Ressource.success(it)))
        } ?: _note.postValue(Event(Ressource.error(NOTE_NOT_FOUND, null)))
    }
}