package com.example.loginapplication.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapplication.usecase.GetForgottenPasswordUseCase
import com.example.loginapplication.usecase.LoginUserUseCase
import com.example.loginapplication.usecase.RegisterUserUseCase
import com.example.loginapplication.usecase.Result
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
    private val registerUserUseCase: RegisterUserUseCase,
    private val getForgottenPasswordUseCase: GetForgottenPasswordUseCase
) : ViewModel() {

    private val _forgotPasswordGetSuccess = MutableSharedFlow<String>()
    val forgotPasswordGetSuccess : Flow<String> = _forgotPasswordGetSuccess

    private val _bottomSheetShow = MutableSharedFlow<Unit>()
    val bottomSheetShow: Flow<Unit> = _bottomSheetShow

    private val _registerSuccess = MutableSharedFlow<Unit>()
    val registerSuccess : Flow<Unit> = _registerSuccess

    private val _error = MutableSharedFlow<LoginErrorType>()
    val error: Flow<LoginErrorType> = _error

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
                        _error.emit(LoginErrorType.LOGIN)
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
                        _error.emit(LoginErrorType.SIGNUP)
                    }
                    RegisterUserUseCase.Result.Success -> {
                        loginUserUseCase(email, password)
                        _registerSuccess.emit(Unit)
                    }
                }
            }
        }
    }

    fun forgotPasswordClicked() {
        Timber.d("showing")
        viewModelScope.launch {
            _bottomSheetShow.emit(Unit)
        }
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

    fun forgotPasswordSubmitClicked(email: String) {
        viewModelScope.launch {
            when(val result = getForgottenPasswordUseCase(email)) {
                is Result.Success -> _forgotPasswordGetSuccess.emit(result.password)
                is Result.Failure -> _error.emit(LoginErrorType.FORGOT_PASSWORD)
            }
        }
    }

    fun onRegistrationSnackBarDismissed() {
        viewModelScope.launch {
            _navigateToApp.emit(Unit)
        }
    }
}

enum class LoginErrorType {
    LOGIN, SIGNUP, FORGOT_PASSWORD
}