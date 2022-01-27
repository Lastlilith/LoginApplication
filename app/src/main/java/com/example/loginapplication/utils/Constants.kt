package com.example.loginapplication.utils

import androidx.datastore.preferences.core.stringPreferencesKey

const val DATASTORE_NAME = "datastore"
const val DATASTORE_LOGGED_IN_EMAIL = "logged_in_email"
@JvmField
val DATASTORE_LOGGED_IN_EMAIL_KEY = stringPreferencesKey(DATASTORE_LOGGED_IN_EMAIL)