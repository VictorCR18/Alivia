package com.example.alivia.ui.components

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(navController: NavHostController, drawerState: DrawerState, scope: CoroutineScope) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .width(380.dp)
            .padding(end = 20.dp)
            .background(MaterialTheme.colorScheme.surface),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Menu",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Perfil",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("profile")
                        // Fecha o drawer ao clicar
                        scope.launch { drawerState.close() }
                    }
                    .padding(vertical = 8.dp)
            )

            Text(
                text = "Configurações",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("settings")
                        // Fecha o drawer ao clicar
                        scope.launch { drawerState.close() }
                    }
                    .padding(vertical = 8.dp)
            )

            Text(
                text = "Sair",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val activity = (navController.context as? Activity)
                        activity?.finish()
                        // Fecha o drawer ao clicar
                        scope.launch { drawerState.close() }
                    }
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Ajuda e Suporte",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("helpAndSupport")
                        // Fecha o drawer ao clicar
                        scope.launch { drawerState.close() }
                    }
                    .padding(vertical = 8.dp)
            )
        }
    }
}
