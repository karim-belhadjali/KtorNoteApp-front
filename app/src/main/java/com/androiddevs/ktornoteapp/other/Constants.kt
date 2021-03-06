package com.androiddevs.ktornoteapp.other

object Constants {

    // INTERCEPTOR CONSTANTS
    val IGNORE_AUTH_URLS= listOf("/login","/register")

    //ENTITIES TABLE NAMES
    const val NOTE_TABLE_NAME= "notes"
    const val DELETED_NOTES_TABLE_NAME= "locally_deleted_note_ids"


    // ROOM AND RETROFIT CONSTANTS
    const val DATABASE_NAME= "note_database"
    const val BASE_URL= "http://10.0.2.2:8002"

    // RESPONSES MESSAGES CONSTANTS
    const val COULDNT_REACH_INTERNET_ERROR= "Couldn't connect to the servers. Check your internet connection"
    const val FILL_ALL_VALUES_ERROR= "Please fill out all the fields"
    const val PASSWORDS_DO_NOT_MATCH= "The passwords do not match"
    const val ACCOUNT_REGISTRED= "Successfully registered an account"
    const val OWNER_ADDED= "Successfully added owner to note "
    const val ACCOUNT_LOGGED_IN= "Successfully logged in"
    const val UNKNOWN_ERROR= "An unknown error has occured"
    const val NOTE_NOT_FOUND= "Note not found"
    const val NOTE__WAS_SUCCESSFULLY_DELETED= "Note was successfully deleted"

    //SHARED PREF CONSTANTS
    const val ENCRYPTED_SHARED_PREF_NAME= "enc_shared_pref"
    const val KEY_LOGGED_IN_EMAIL= "KEY_LOGGED_IN_EMAIL"
    const val KEY_PASSWORD= "KEY_PASSWORD"
    const val NO_EMAIL= "NO_EMAIL"
    const val NO_PASSWORD= "NO_PASSWORD"


    // COLORS AND OTHER CONSTANTS
    const val DEFAULT_NOTE_COLOR= "FFA500"

}