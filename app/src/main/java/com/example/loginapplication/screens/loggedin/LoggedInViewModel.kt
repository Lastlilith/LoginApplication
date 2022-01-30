package com.example.loginapplication.screens.loggedin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapplication.usecase.LogOutUseCase
import com.example.loginapplication.usecase.ObserveLoggedInUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoggedInViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val observeLoggedInUserUseCase: ObserveLoggedInUserUseCase
): ViewModel() {

    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email


    init {
        viewModelScope.launch {
            observeLoggedInUserUseCase().onEach {user ->
                Timber.d(user.toString())
                if(user != null) {
                    _email.emit(user.email)
                }
            }.launchIn(this)
        }
    }

    fun logOutClicked() {
        viewModelScope.launch {
            logOutUseCase()
            Timber.d("Logout clicked invoked")
        }
    }
}