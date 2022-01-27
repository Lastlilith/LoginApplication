package com.example.loginapplication.di

import android.content.Context
import androidx.room.Room
import com.example.loginapplication.model.db.ApplicationDatabase
import com.example.loginapplication.model.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun applicationDatabase(@ApplicationContext context: Context): ApplicationDatabase {
        return Room
            .databaseBuilder(context, ApplicationDatabase::class.java, "database")
            .build()
    }

    @Provides
    @Singleton
    fun userDao(applicationDatabase: ApplicationDatabase): UserDao {
        return applicationDatabase.userDao()
    }
}