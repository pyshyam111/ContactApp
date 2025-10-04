package com.shyam.contactapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shyam.contactapp.data.local.entity.ContactEntity
import com.shyam.contactapp.data.repository.ContactRepository
import com.shyam.contactapp.presentation.state.ContactState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val repository: ContactRepository
) : ViewModel() {

    private val isSortedByName = MutableStateFlow(true)

    private val contacts: StateFlow<List<ContactEntity>> = isSortedByName
        .flatMapLatest { sortByName ->
            if (sortByName) repository.getContactsSortedByName()
            else repository.getContactsSortedByDate()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _state = MutableStateFlow(ContactState())
    val state: StateFlow<ContactState> = combine(
        _state, contacts, isSortedByName
    ) { currentState, contacts, _ ->
        currentState.copy(contacts = contacts)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())

    fun changeIsSorting() {
        isSortedByName.value = !isSortedByName.value
    }

    fun saveContact() {
        val contact = ContactEntity(   // ✅ ContactEntity use karo, Contact nahi
            id = state.value.id.value,
            name = state.value.name.value,
            phone = state.value.phone.value,
            email = state.value.email.value,
            image = state.value.image.value,
            dateOfCreation = System.currentTimeMillis()
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertContact(contact)  // ✅ database write background thread
        }
        resetState()
    }

    fun deleteContact() {
        val contact = ContactEntity(
            id = state.value.id.value,
            name = state.value.name.value,
            phone = state.value.phone.value,
            email = state.value.email.value,
            dateOfCreation = state.value.dateOfCreation.value,
            image = state.value.image.value
        )
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
        resetState()
    }

    private fun resetState() {
        _state.value = ContactState()
    }
}
