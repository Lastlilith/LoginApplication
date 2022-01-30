package com.example.loginapplication.usecase

import com.example.loginapplication.utils.DATASTORE_LOGGED_IN_EMAIL_KEY
import com.example.loginapplication.utils.DatastoreManager
import timber.log.Timber
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val datastoreManager: DatastoreManager
){
    suspend operator fun invoke() {
        Timber.d("Progressing")
        datastoreManager.removeFromDataStore(DATASTORE_LOGGED_IN_EMAIL_KEY)
    }
}