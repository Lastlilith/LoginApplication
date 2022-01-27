package com.example.loginapplication.usecase

import com.example.loginapplication.model.db.dto.UserDto
import timber.log.Timber
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val addUserToDatabaseUseCase: AddUserToDatabaseUseCase,
    private val checkIfUserExistsUseCase: CheckIfUserExistsUseCase
) {
    operator fun invoke(email: String, password: String): Result {
        Timber.d("invoke: $email")
        val userExists = checkIfUserExistsUseCase(email)
        return if (!userExists) {
            addUserToDatabaseUseCase(UserDto(email = email, password = password))
            Timber.d("Success!")
            Result.Success
        } else {
            Timber.d("Failure")
            Result.Failure
        }
    }

    sealed class Result {
        object Success : Result()
        object Failure : Result()
    }
}