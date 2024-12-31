package com.example.alivia.ui.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.alivia.ui.components.createNotificationChannel
import com.example.alivia.ui.components.sendNotification

@Composable
fun SettingsScreen(
    navController: NavHostController,
    onThemeToggle: () -> Unit, // Callback para alternar tema
    isDarkThemeEnabled: Boolean, // Estado atual do tema
    context: Context // Contexto para enviar notificações
) {
    // Estado para notificações
    val isNotificationsEnabled = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {

        // Row para alinhar ícone e título na horizontal
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Menu",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        navController.navigate("home")
                    }
            )

            Spacer(modifier = Modifier.padding(8.dp)) // Espaço entre o ícone e o título

            Text(
                text = "Configurações",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        // Opção de Notificações
        RowOption(
            title = "Notificações",
            description = "Ativar/desativar notificações do aplicativo",
            isChecked = isNotificationsEnabled.value,
            onCheckedChange = {
                isNotificationsEnabled.value = it
                if (it) {
                    // Ativar notificações
                    createNotificationChannel(context)
                    sendNotification(context, "Notificação ativada")
                } else {
                    // Desativar notificações
                    sendNotification(context, "Notificação desativada")
                }
            }
        )

        Spacer(modifier = Modifier.size(6.dp))

        // Opção de Tema Escuro
        RowOption(
            title = "Tema Escuro",
            description = "Alternar entre tema claro e escuro",
            isChecked = isDarkThemeEnabled,
            onCheckedChange = { onThemeToggle() }
        )
    }
}

@Composable
fun RowOption(
    title: String,
    description: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start // Alinha os itens à esquerda
    ) {
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(end = 16.dp) // Espaço à direita do Switch
        )

        Column(
            modifier = Modifier.weight(1f) // Faz a descrição e título ocuparem o restante do espaço
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }
    }
}

