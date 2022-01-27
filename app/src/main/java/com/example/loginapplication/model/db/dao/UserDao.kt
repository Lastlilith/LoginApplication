package com.example.loginapplication.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loginapplication.model.db.dto.UserDto

@Dao
interface UserDao {

    @Query("SELECT * FROM UserDto WHERE email=:email  ")
    fun findByEmail(email: String): UserDto

    @Query("SELECT EXISTS(SELECT * FROM UserDto WHERE email =:email)")
    fun isRowExists(email: String): Boolean

    @Insert
    fun insert(user: UserDto)
}