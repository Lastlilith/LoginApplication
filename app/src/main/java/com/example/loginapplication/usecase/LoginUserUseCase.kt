package com.example.loginapplication.usecase

import timber.log.Timber
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
    private val addLoggedInEmailToDatastoreUseCase: AddLoggedInEmailToDatastoreUseCase
) {

    suspend operator fun invoke(email: String, password: String): Result {
        Timber.d("invoke: $email")
        try {
            val userDto = getUserByEmailUseCase(email)
            if (userDto.password != password) {
                Timber.e("LoginUserUseCase: failed, passwords do not match")
                return Result.Failure
            }
            addLoggedInEmailToDatastoreUseCase(email)
            return Result.Success
        } catch (e: Exception) {
            Timber.e("LoginUserUseCase: failed, exception: ${e.message}")
            return Result.Failure
        }
    }

    sealed class Result {
        object Success : Result()
        object Failure : Result()
    }
}