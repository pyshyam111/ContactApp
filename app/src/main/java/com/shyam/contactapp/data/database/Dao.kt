package com.shyam.contactapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.shyam.contactapp.data.local.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Upsert
    suspend fun upsertContact(contact: ContactEntity)

    @Delete
    suspend fun deleteContact(contact: ContactEntity)

    @Query("SELECT * FROM contacts ORDER BY name ASC")
    fun getContactsSortedByName(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts ORDER BY dateOfCreation DESC")
    fun getContactsSortedByDate(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): Flow<List<ContactEntity>>
}
