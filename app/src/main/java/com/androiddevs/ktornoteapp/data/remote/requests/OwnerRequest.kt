package com.androiddevs.ktornoteapp.data.remote.requests

data class OwnerRequest(
    val noteId: String,
    val newOwner: String
)