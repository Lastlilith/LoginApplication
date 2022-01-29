package com.example.loginapplication.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapplication.usecase.LoginUserUseCase
import com.example.loginapplication.usecase.RegisterUserUseCase
import com.example.loginapplication.utils.isValidEmail
import com.example.loginapplication.utils.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _registerSuccess = MutableSharedFlow<Unit>()
    val registerSuccess : Flow<Unit> = _registerSuccess

    private val _error = MutableSharedFlow<Unit>()
    val error: Flow<Unit> = _error

    private val _navigateToApp = MutableSharedFlow<Unit>()
    val navigateToApp: Flow<Unit> = _navigateToApp

    private val _state = MutableStateFlow(LoginState())
    val state : Flow<LoginState> = _state

    init {
        Timber.d("init")
    }

    fun loginClicked(email: String, password: String) {
        Timber.d("loginClicked: $email, $password")
        if(validateInput(email, password)) {
            viewModelScope.launch {
               when(loginUserUseCase(email, password)) {
                    LoginUserUseCase.Result.Failure -> {
                        _error.emit(Unit)
                    }
                    LoginUserUseCase.Result.Success -> _navigateToApp.emit(Unit)
                }
            }
        }
    }

    fun signUpClicked(email: String, password: String) {
        if(validateInput(email, password)) {
            viewModelScope.launch {
                when(registerUserUseCase(email, password)) {
                    RegisterUserUseCase.Result.Failure -> {
                        _error.emit(Unit)
                    }
                    RegisterUserUseCase.Result.Success -> _registerSuccess.emit(Unit)
                }
            }
        }
    }

    fun forgotPasswordClicked() {

    }

    private fun validateInput(email: String, password: String): Boolean {
        val isEmailValid = email.isValidEmail()
        val isPasswordValid = password.isValidPassword()

        _state.value = _state.value.copy(
            isEmailValid = isEmailValid,
            isPasswordValid = isPasswordValid
        )

        return isEmailValid && isPasswordValid
    }
}