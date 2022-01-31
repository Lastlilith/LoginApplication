package com.example.loginapplication.usecase

import com.example.loginapplication.utils.DATASTORE_LOGGED_IN_EMAIL_KEY
import com.example.loginapplication.utils.DatastoreManager
import timber.log.Timber
import javax.inject.Inject

open class AddLoggedInEmailToDatastoreUseCase @Inject constructor(
    private val datastoreManager: DatastoreManager
) {
   open suspend operator fun invoke(email: String) {
        Timber.d("invoke: $email")
        datastoreManager.addToDatastore(DATASTORE_LOGGED_IN_EMAIL_KEY, email)
    }
}