package com.androiddevs.ktornoteapp.repositories

import android.app.Application
import android.content.Context
import com.androiddevs.ktornoteapp.data.local.NoteDao
import com.androiddevs.ktornoteapp.data.remote.NoteApi
import com.androiddevs.ktornoteapp.data.remote.requests.AccountRequest
import com.androiddevs.ktornoteapp.other.Constants.COULDNT_REACH_INTERNET_ERROR
import com.androiddevs.ktornoteapp.other.Ressource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val noteApi: NoteApi,
    private val context: Application
) {
    suspend fun register(email: String, password: String) = withContext(Dispatchers.IO) {
        try {
            val response = noteApi.register(AccountRequest(email, password))
            if (response.isSuccessful && response.body()!!.successful) {
                Ressource.success(response.body()?.message)
            } else {
                Ressource.error(response.body()?.message ?: response.message(), null)
            }
        } catch (e: Exception) {
            Ressource.error(COULDNT_REACH_INTERNET_ERROR, null)
        }
    }

    suspend fun login(email: String, password: String) = withContext(Dispatchers.IO) {
        try {
            val response = noteApi.login(AccountRequest(email, password))
            if (response.isSuccessful && response.body()!!.successful) {
                Ressource.success(response.body()?.message)
            } else {
                Ressource.error(response.body()?.message ?: response.message(), null)
            }
        } catch (e: Exception) {
            Ressource.error(COULDNT_REACH_INTERNET_ERROR, null)
        }
    }
}