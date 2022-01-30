package com.example.loginapplication.usecase

import timber.log.Timber
import javax.inject.Inject

class GetForgottenPasswordUseCase @Inject constructor(
    private val getUserByEmailUseCase: GetUserByEmailUseCase
) {
    suspend operator fun invoke(email: String): String {
        Timber.d(email)
        return getUserByEmailUseCase(email).password
    }
}