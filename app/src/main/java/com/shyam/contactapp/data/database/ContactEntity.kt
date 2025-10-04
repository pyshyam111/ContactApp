package com.shyam.contactapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phone: String,
    val email: String? = null,
    val image: ByteArray? = null,
    val dateOfCreation: Long = System.currentTimeMillis()
)
