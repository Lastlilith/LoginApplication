package com.example.loginapplication

import com.example.loginapplication.model.db.dto.UserDto
import com.example.loginapplication.usecase.AddLoggedInEmailToDatastoreUseCase
import com.example.loginapplication.usecase.GetUserByEmailUseCase
import com.example.loginapplication.usecase.LoginUserUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class LoginUserUseCaseTest {

    private lateinit var getUserByEmailUseCase: GetUserByEmailUseCase
    private lateinit var addLoggedInEmailToDatastoreUseCase: AddLoggedInEmailToDatastoreUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    private fun setupUseCase(
        getUserByEmailUseCase: GetUserByEmailUseCase = mock(GetUserByEmailUseCase::class.java),
        addLoggedInEmailToDatastoreUseCase: AddLoggedInEmailToDatastoreUseCase = mock(
            AddLoggedInEmailToDatastoreUseCase::class.java
        )
    ): LoginUserUseCase {
        this.getUserByEmailUseCase = getUserByEmailUseCase
        this.addLoggedInEmailToDatastoreUseCase = addLoggedInEmailToDatastoreUseCase

        return LoginUserUseCase(
            this.getUserByEmailUseCase,
            this.addLoggedInEmailToDatastoreUseCase
        )
    }

    @Test
    fun userNotFound() {
        val email = "email"
        val password = "password"

        val useCase = setupUseCase(
            getUserByEmailUseCase = mock(GetUserByEmailUseCase::class.java) {
                throw Exception("User not found")
            }
        )
        runTest {
            val result = useCase(email, password)
            verify(getUserByEmailUseCase, atLeastOnce()).invoke(email)
            assertEquals(LoginUserUseCase.Result.Failure, result)
        }
    }

    @Test
    fun passwordsDoNotMatch() {
        val email = "email"
        val dbPassword = "user's actual password"
        val methodInputPassword = "password"

        val useCase = setupUseCase(getUserByEmailUseCase = mock(GetUserByEmailUseCase::class.java){
            UserDto(0, "email", dbPassword)
        })
        runTest {
            val result = useCase(email, methodInputPassword)
            verify(getUserByEmailUseCase, atLeastOnce()).invoke(email)
            assertEquals(LoginUserUseCase.Result.Failure, result)
        }
    }

    @Test
    fun success() {
        val email = "email"
        val methodInputPassword = "password"

        val useCase = setupUseCase(getUserByEmailUseCase = mock(GetUserByEmailUseCase::class.java){
            UserDto(0, "email", methodInputPassword)
        })

        runTest {
            val result = useCase(email, methodInputPassword)
            verify(getUserByEmailUseCase, atLeastOnce()).invoke(email)
            verify(addLoggedInEmailToDatastoreUseCase, atLeastOnce()).invoke(email)
            assertEquals(LoginUserUseCase.Result.Success, result)
        }
    }
}