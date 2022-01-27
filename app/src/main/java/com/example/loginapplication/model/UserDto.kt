package com.example.loginapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDto(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val email: String,
    val password: String
)