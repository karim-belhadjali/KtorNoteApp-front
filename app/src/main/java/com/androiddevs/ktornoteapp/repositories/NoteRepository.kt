package com.androiddevs.ktornoteapp.repositories

import android.app.Application
import com.androiddevs.ktornoteapp.data.local.NoteDao
import com.androiddevs.ktornoteapp.data.local.entites.LocallyDeletedNoteID
import com.androiddevs.ktornoteapp.data.local.entites.Note
import com.androiddevs.ktornoteapp.data.remote.BasicAuthInterceptor
import com.androiddevs.ktornoteapp.data.remote.NoteApi
import com.androiddevs.ktornoteapp.data.remote.requests.AccountRequest
import com.androiddevs.ktornoteapp.data.remote.requests.DeleteNoteRequest
import com.androiddevs.ktornoteapp.data.remote.requests.OwnerRequest
import com.androiddevs.ktornoteapp.other.Constants.COULDNT_REACH_INTERNET_ERROR
import com.androiddevs.ktornoteapp.other.Ressource
import com.androiddevs.ktornoteapp.other.checkForInternetConnection
import com.androiddevs.ktornoteapp.other.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val noteApi: NoteApi,
    private val context: Application,
    private val basicAuthInterceptor: BasicAuthInterceptor
) {

    suspend fun insertNote(note: Note) {
        val response = try {
            noteApi.addNote(note)
        } catch (e: Exception) {
            null
        }
        if (response != null && response.isSuccessful) {
            noteDao.insertNote(note.apply { isSynced = true })
        } else {
            noteDao.insertNote(note)
        }
    }

    fun observeNoteByID(noteID: String) = noteDao.observeNoteById(noteID)

    suspend fun insertNotes(notes: List<Note>) {
        notes.forEach { insertNote(it) }
    }

    suspend fun deleteNote(noteID: String) {
        val response = try {
            noteApi.deleteNote(DeleteNoteRequest(noteID))
        } catch (e: Exception) {
            null
        }
        noteDao.deleteNoteById(noteID)
        if (response == null || !response.isSuccessful) {
            noteDao.insertLocallyDeletedNoteId(LocallyDeletedNoteID(noteID))
        } else {
            deleteLocallyDeletedNoteID(noteID)
        }
    }

    suspend fun deleteLocallyDeletedNoteID(deletedNoteID: String) {
        noteDao.deleteLocallyDeletedNoteID(deletedNoteID)
    }

    suspend fun getNoteById(noteID: String): Note? {
        return noteDao.getNoteById(noteID)
    }


    fun getAllNotes(): Flow<Ressource<List<Note>>> {
        val owner ="%${basicAuthInterceptor.email!!}%"
        return networkBoundResource(
            query = {
                noteDao.getAllNotes(owner)
            },
            fetch = {
                syncNotes()
            },
            saveFetchResult = { response ->
                response?.body()?.let {
                    insertNotes(it)
                }
            },
            shouldFetch = {
                checkForInternetConnection(context)
            }

        )
    }


    suspend fun syncNotes(): Response<List<Note>>? {
        val locallyDeletedNoteIDs = noteDao.getAllLocallyDeletedNoteID()
        locallyDeletedNoteIDs.forEach { id ->
            deleteNote(id.deletedNoteID)
        }
        val unSyncedNotes = noteDao.getAllUnsyncedNotes()
        unSyncedNotes.forEach { note ->
            insertNote(note)
        }
        return noteApi.getNotes()

    }

    suspend fun register(email: String, password: String) = withContext(Dispatchers.IO) {
        try {
            val response = noteApi.register(AccountRequest(email, password))
            if (response.isSuccessful && response.body()!!.successful) {
                Ressource.success(response.body()?.message ?: response.message())
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
                Ressource.success(response.body()?.message ?: response.message())
            } else {
                Ressource.error(response.body()?.message ?: response.message(), null)
            }
        } catch (e: Exception) {
            Ressource.error(COULDNT_REACH_INTERNET_ERROR, null)
        }
    }

    suspend fun addOwnerToNote(owner: String, noteID: String) = withContext(Dispatchers.IO) {
        try {
            val response = noteApi.addOwnerToNote(OwnerRequest(noteID, owner))
            if (response.isSuccessful && response.body()!!.successful) {
                Ressource.success(response.body()?.message ?: response.message())
            } else {
                Ressource.error(response.body()?.message ?: response.message(), null)
            }
        } catch (e: Exception) {
            Ressource.error(COULDNT_REACH_INTERNET_ERROR, null)
        }
    }
}