package com.example.loginapplication.usecase

import com.example.loginapplication.model.db.dao.UserDao
import timber.log.Timber
import javax.inject.Inject

class CheckIfUserExistsUseCase @Inject constructor(
    private val userDao: UserDao
) {

    suspend operator fun invoke(email: String): Boolean {
        Timber.d("invoke $email")
       return userDao.isRowExists(email)
    }
}