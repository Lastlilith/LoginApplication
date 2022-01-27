package com.example.loginapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.loginapplication.model.UserDto

@Database(entities = [UserDto::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {

}