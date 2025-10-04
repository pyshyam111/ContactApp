package com.shyam.contactapp.data.repository

import com.shyam.contactapp.data.database.ContactDao
import com.shyam.contactapp.data.local.entity.ContactEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepository @Inject constructor(
    private val dao: ContactDao
) {
    fun getContactsSortedByName(): Flow<List<ContactEntity>> = dao.getContactsSortedByName()
    fun getContactsSortedByDate(): Flow<List<ContactEntity>> = dao.getContactsSortedByDate()

    suspend fun upsertContact(contact: ContactEntity) = dao.upsertContact(contact)
    suspend fun deleteContact(contact: ContactEntity) = dao.deleteContact(contact)
}
