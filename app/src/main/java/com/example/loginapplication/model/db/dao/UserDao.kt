package com.example.loginapplication.model.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.loginapplication.model.db.dto.UserDto

@Dao
interface UserDao {

    @Query("SELECT * FROM UserDto WHERE email=:email  ")
    fun findByEmail(email: String): UserDto
}