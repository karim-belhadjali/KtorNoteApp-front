package com.androiddevs.ktornoteapp.ui.auth

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.ktornoteapp.other.Constants.FILL_ALL_VALUES_ERROR
import com.androiddevs.ktornoteapp.other.Constants.PASSWORDS_DO_NOT_MATCH
import com.androiddevs.ktornoteapp.other.Ressource
import com.androiddevs.ktornoteapp.repositories.NoteRepository
import kotlinx.coroutines.launch

class AuthViewModel @ViewModelInject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _registerStatus = MutableLiveData<Ressource<String>>()
    val registerStatus: LiveData<Ressource<String>> = _registerStatus

    private val _loginStatus = MutableLiveData<Ressource<String>>()
    val loginStatus: LiveData<Ressource<String>> = _loginStatus

    fun register(email: String, password: String, repeatedPassword: String) {
        _registerStatus.postValue(Ressource.loading(null))
        if (email.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()) {
            _registerStatus.postValue(Ressource.error(FILL_ALL_VALUES_ERROR, null))
            return
        }
        if (password != repeatedPassword) {
            _registerStatus.postValue(Ressource.error(PASSWORDS_DO_NOT_MATCH, null))
            return
        }
        viewModelScope.launch {
            val result = repository.register(email, password)
            _registerStatus.postValue(result)
        }
    }

    fun login(email: String, password: String) {
        _loginStatus.postValue(Ressource.loading(null))
        if (email.isEmpty() || password.isEmpty()) {
            _loginStatus.postValue(Ressource.error(FILL_ALL_VALUES_ERROR, null))
            return
        }

        viewModelScope.launch {
            val result = repository.login(email, password)
            _loginStatus.postValue(result)
        }
    }
}