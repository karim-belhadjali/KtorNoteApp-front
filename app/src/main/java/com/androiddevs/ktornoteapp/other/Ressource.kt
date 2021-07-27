package com.androiddevs.ktornoteapp.other

import javax.annotation.Resource

data class Ressource<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {

    companion object {
        fun <T> success(data: T?): Ressource<T> {
            return Ressource(Status.SUCCES, data, null)
        }

        fun <T> error(msg: String, data: T?): Ressource<T> {
            return Ressource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Ressource<T> {
            return Ressource(Status.LOADING, data, null)
        }
    }
}

