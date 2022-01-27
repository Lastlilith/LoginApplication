package com.example.loginapplication.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loginapplication.model.db.dto.UserDto

@Dao
interface UserDao {

    @Query("SELECT * FROM UserDto WHERE email=:email  ")
    suspend fun findByEmail(email: String): UserDto

    @Query("SELECT EXISTS(SELECT * FROM UserDto WHERE email =:email)")
    suspend fun isRowExists(email: String): Boolean

    @Insert
    suspend fun insert(user: UserDto)
}