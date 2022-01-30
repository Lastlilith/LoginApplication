package com.example.loginapplication.screens.loggedin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapplication.usecase.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoggedInViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase
): ViewModel() {

    fun logOutClicked() {
        viewModelScope.launch {
            logOutUseCase()
            Timber.d("Logout clicked invoked")
        }
    }
}