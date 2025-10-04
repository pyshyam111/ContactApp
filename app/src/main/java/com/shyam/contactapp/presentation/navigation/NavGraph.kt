package com.shyam.contactapp.presentation.navigation


import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shyam.contactapp.presentation.ContactViewModel
import com.shyam.contactapp.presentation.screen.AddEditScreen
import com.shyam.contactapp.presentation.screen.HomeScreen
import com.shyam.contactapp.presentation.state.ContactState


@Composable
fun NavGraph(navHostController: NavHostController, viewModel: ContactViewModel) {

    val state: ContactState by viewModel.state.collectAsState(initial = ContactState())

    val navController = rememberNavController()

    NavHost(
        navController = navHostController, // use same instance
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            HomeScreen(navHostController = navHostController, state = state, viewModel = viewModel)
        }
        composable(Routes.AddEdit.route) {
            AddEditScreen(
                state = state,
                navHostController = navHostController,
                onEvent = { viewModel.saveContact() }
            )
        }
    }
        composable(Routes.Home.route) {
            HomeScreen(
                navHostController = navHostController,
                state = state, // 👈 ऊपर वाला collectAsState इस्तेमाल हो रहा है
                viewModel = viewModel
            )
        }
    }

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    state: ContactState,
    viewModel: ContactViewModel
) {
    TODO("Not yet implemented")
}

fun composable(route: String, function: @Composable () -> Unit) {}
