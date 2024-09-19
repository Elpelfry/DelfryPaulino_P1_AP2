package edu.ucne.delfrypaulino_p1_ap2.presentation.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

@Composable
fun DelfryPaulino_P1_AP2NavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ListScreen,
    ) {
        composable<Screen.ListScreen> {
            Button(
                onClick = {
                    navController.navigate(Screen.RegistroScreen(0))
                }
            ) {
                Text("Registro")
                }

        }
        composable<Screen.RegistroScreen> {
            val args = it.toRoute<Screen.RegistroScreen>()
            Button(
                onClick = {
                    navController.navigate(Screen.ListScreen)
                }
            ) {
                Text("List")
            }

        }

    }
}