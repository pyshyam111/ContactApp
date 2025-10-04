package com.shyam.contactapp.presentation.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.shyam.contactapp.data.local.entity.ContactEntity

data class ContactState(
    val id: MutableState<Int> = mutableStateOf(0),
    val name: MutableState<String> = mutableStateOf(""),
    val phone: MutableState<String> = mutableStateOf(""),
    val email: MutableState<String> = mutableStateOf(""),
    val image: MutableState<ByteArray?> = mutableStateOf(null),
    val dateOfCreation: MutableState<Long> = mutableStateOf(System.currentTimeMillis()),
    val contacts: List<ContactEntity> = emptyList()
)
