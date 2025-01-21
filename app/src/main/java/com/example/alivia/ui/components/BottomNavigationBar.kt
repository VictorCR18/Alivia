package com.example.alivia.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        val items = listOf(
            Triple("home", "Home", Icons.Default.Home),
            Triple("favorites", "Favoritos", Icons.Default.Favorite)
        )

        // Obtém a rota atual
        val currentRoute = navController.currentBackStackEntry?.destination?.route

        items.forEach { (route, label, icon) ->
            NavigationBarItem(
                icon = {
                    Icon(
                        icon,
                        contentDescription = label,
                        tint = Color(0xFF267A9C)
                    )
                },
                label = { Text(label) },
                selected = currentRoute == route,
                onClick = {
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = false // Não remove a rota inicial do grafo
                            }
                            launchSingleTop = true // Evita criar múltiplas instâncias da mesma rota
                        }
                    }
                }
            )
        }
    }
}
