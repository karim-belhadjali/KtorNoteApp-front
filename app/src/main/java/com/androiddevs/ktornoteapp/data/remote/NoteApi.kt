package com.androiddevs.ktornoteapp.data.remote

import com.androiddevs.ktornoteapp.data.local.entites.Note
import com.androiddevs.ktornoteapp.data.remote.requests.AccountRequest
import com.androiddevs.ktornoteapp.data.remote.requests.DeleteNoteRequest
import com.androiddevs.ktornoteapp.data.remote.requests.OwnerRequest
import com.androiddevs.ktornoteapp.data.remote.responses.SimpleResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.annotation.PostConstruct

interface NoteApi {


    @POST("/register")
    suspend fun register(
        @Body registerRequest: AccountRequest
    ): Response<SimpleResponse>

    @POST("/login")
    suspend fun login(
        @Body loginRequest: AccountRequest
    ): Response<SimpleResponse>

    @POST("/addNote")
    suspend fun addNote(
        @Body note: Note
    ): Response<ResponseBody>

    @POST("/deleteNote")
    suspend fun deleteNote(
        @Body deleteNoteRequest: DeleteNoteRequest
    ): Response<ResponseBody>

    @POST("/addOwner")
    suspend fun addOwnerToNote(
        @Body addOwnerRequest: OwnerRequest
    ): Response<SimpleResponse>

    @GET("/getNotes")
    suspend fun getNotes(): Response<List<Note>>

}