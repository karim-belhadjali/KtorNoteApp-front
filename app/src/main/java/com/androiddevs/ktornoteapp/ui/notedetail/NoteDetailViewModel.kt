package com.androiddevs.ktornoteapp.ui.notedetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.ktornoteapp.other.Constants.FILL_ALL_VALUES_ERROR
import com.androiddevs.ktornoteapp.other.Event
import com.androiddevs.ktornoteapp.other.Ressource
import com.androiddevs.ktornoteapp.repositories.NoteRepository
import kotlinx.coroutines.launch

class NoteDetailViewModel @ViewModelInject constructor(
    private val repository: NoteRepository
): ViewModel() {

    private  val _addOwnerStatus = MutableLiveData<Event<Ressource<String>>>()
    val addOwnerStatus : LiveData<Event<Ressource<String>>> = _addOwnerStatus

    fun addOwnerToNote(owner : String, noteID: String){
        _addOwnerStatus.postValue(Event((Ressource.loading(null))))
        if (owner.isEmpty()||noteID.isEmpty()){
            _addOwnerStatus.postValue(Event(Ressource.error(FILL_ALL_VALUES_ERROR,null)))
            return
        }

        viewModelScope.launch {
            val result = repository.addOwnerToNote(owner,noteID)
            _addOwnerStatus.postValue(Event(result))
        }
    }

    fun observeNoteByID(noteID : String) = repository.observeNoteByID(noteID)

}