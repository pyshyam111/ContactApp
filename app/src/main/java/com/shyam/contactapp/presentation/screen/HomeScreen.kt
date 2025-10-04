package com.shyam.contactapp.presentation.screen

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.shyam.contactapp.presentation.ContactViewModel
import com.shyam.contactapp.presentation.navigation.Routes
import com.shyam.contactapp.presentation.state.ContactState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    state: ContactState,
    viewModel: ContactViewModel
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Keeper") },
                actions = {
                    IconButton(onClick = { viewModel.changeIsSorting() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Sort"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(Routes.AddEdit.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { innerpadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
        ) {
            LazyColumn {
                items(state.contacts) { contact ->
                    val bitmap: ImageBitmap = contact.image?.let { imageBytes ->
                        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                            .asImageBitmap()
                    } ?: ImageBitmap(1, 1) // placeholder

                    contactCard(
                        viewModel = viewModel,
                        state = state,
                        name = contact.name,
                        phone = contact.phone,
                        email = contact.email,
                        image = bitmap,
                        imageByteArray = contact.image,
                        id = contact.id,
                        dataOfCreation = contact.dateOfCreation,
                        navHostController = navHostController
                    )
                }
            }


        }
    }
}

@Composable
fun contactCard(
    name: String,
    phone: String,
    email: String?,
    image: ImageBitmap,
    imageByteArray: ByteArray?,
    dataOfCreation: Long,
    id: Int,
    viewModel: ContactViewModel,
    state: ContactState,
    navHostController: NavHostController
) {
    val context = LocalContext.current

    // Permission launcher for CALL_PHONE
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission mil gayi -> ab call start kar do
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$phone")
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Call permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Card(
        onClick = {
            state.id.value = id
            state.name.value = name
            state.phone.value = phone
            if (email != null) state.email.value = email
            state.image.value = imageByteArray
            state.dateOfCreation.value = dataOfCreation
            navHostController.navigate(Routes.AddEdit.route)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            // Contact image
            if (image.width > 1 && image.height > 1) {
                Image(
                    bitmap = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                        .padding(16.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Contact details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = phone,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                if (email != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = email,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Action buttons (Delete & Call)
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Delete button
                IconButton(onClick = {
                    state.id.value = id
                    state.name.value = name
                    state.phone.value = phone
                    if (email != null) state.email.value = email
                    state.dateOfCreation.value = dataOfCreation
                    viewModel.deleteContact()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete contact",
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                // Call button with permission handling
                IconButton(onClick = {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            android.Manifest.permission.CALL_PHONE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        val intent = Intent(Intent.ACTION_CALL)
                        intent.data = Uri.parse("tel:$phone")
                        context.startActivity(intent)
                    } else {
                        requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call contact",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}