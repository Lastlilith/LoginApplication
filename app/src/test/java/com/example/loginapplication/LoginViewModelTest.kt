package com.example.loginapplication

import app.cash.turbine.test
import com.example.loginapplication.screens.login.LoginState
import com.example.loginapplication.screens.login.LoginViewModel
import com.example.loginapplication.usecase.GetForgottenPasswordUseCase
import com.example.loginapplication.usecase.LoginUserUseCase
import com.example.loginapplication.usecase.RegisterUserUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class LoginViewModelTest {
    private lateinit var loginUserUseCase: LoginUserUseCase
    private lateinit var registerUserUseCase: RegisterUserUseCase
    private lateinit var getForgottenPasswordUseCase: GetForgottenPasswordUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        loginUserUseCase = mock(LoginUserUseCase::class.java)
        registerUserUseCase = mock(RegisterUserUseCase::class.java)
        getForgottenPasswordUseCase = mock((GetForgottenPasswordUseCase::class.java))
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loginClicked_validateInputsErrors() {
        val viewModel = LoginViewModel(
            loginUserUseCase,
            registerUserUseCase,
            getForgottenPasswordUseCase
        )
        val invalidUsername = ""
        val invalidPassword = ""
        viewModel.loginClicked(invalidUsername, invalidPassword)

        runBlocking {
            viewModel.state.test {
                assertEquals(LoginState(false, false), awaitItem())
            }
        }
    }
}