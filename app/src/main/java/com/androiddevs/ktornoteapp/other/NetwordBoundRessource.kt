package com.androiddevs.ktornoteapp.other

import com.androiddevs.ktornoteapp.other.Constants.COULDNT_REACH_INTERNET_ERROR
import kotlinx.coroutines.flow.*
import java.lang.Exception


inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query:  () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchedFailed: (Throwable) -> Unit = {},
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    emit(Ressource.loading(null))
    val data = query().first()
    val flow = if (shouldFetch(data)) {
        emit(Ressource.loading(data))

        try {
            val fetchedResult = fetch()
            saveFetchResult(fetchedResult)
            query().map {
                Ressource.success(it)
            }
        } catch (t: Throwable) {
            onFetchedFailed(t)
            query().map {
                Ressource.error(COULDNT_REACH_INTERNET_ERROR, it)
            }
        }
    } else {
        query().map {
            Ressource.success(it)
        }
    }

    emitAll(flow)
}