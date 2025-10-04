package com.shyam.contactapp.data.di

import android.app.Application
import androidx.room.Room
import com.shyam.contactapp.data.local.ContactDatabase
import com.shyam.contactapp.data.database.ContactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): ContactDatabase {
        return Room.databaseBuilder(
            app,
            ContactDatabase::class.java,
            "contact_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    // ✅ DAO provide karna yahan mandatory hai
    @Provides
    fun provideContactDao(db: ContactDatabase): ContactDao {
        return db.contactDao()
    }
}
