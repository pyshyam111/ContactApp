package com.shyam.contactapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val phone: String,
    val email: String,
    val image: ByteArray?,           // images as ByteArray
    val dateOfCreation: Long
)