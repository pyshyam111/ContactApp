package com.shyam.contactapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shyam.contactapp.data.database.ContactDao
import com.shyam.contactapp.data.local.entity.ContactEntity

@Database(
    entities = [ContactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}
