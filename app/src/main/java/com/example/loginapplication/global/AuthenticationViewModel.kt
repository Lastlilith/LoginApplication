package com.example.loginapplication.global

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapplication.usecase.ObserveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val observeUserUseCase: ObserveUserUseCase
): ViewModel() {

    private val _logOut = MutableSharedFlow<Unit>()
    val logOut: Flow<Unit> = _logOut

    init {
        viewModelScope.launch {
            observeUserUseCase().onEach {user ->
                if(user == null) {
                    _logOut.emit(Unit)
                }
            }.launchIn(this)
        }
    }
}