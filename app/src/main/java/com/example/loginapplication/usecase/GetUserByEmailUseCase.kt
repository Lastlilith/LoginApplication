package com.example.loginapplication.usecase

import com.example.loginapplication.model.db.dao.UserDao
import com.example.loginapplication.model.db.dto.UserDto
import timber.log.Timber
import javax.inject.Inject

class GetUserByEmailUseCase @Inject constructor(private val userDao: UserDao) {

    operator fun invoke(email: String): UserDto {
        Timber.d("invoke: $email")
        return userDao.findByEmail(email)
    }
}