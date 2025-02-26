package com.example.alivia.ui.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.alivia.ui.components.createNotificationChannel
import com.example.alivia.ui.components.sendNotification
import com.example.alivia.viewmodel.SettingsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    onThemeChange: (String) -> Unit, // Função de callback para a mudança de tema
    settingsViewModel: SettingsViewModel,
    context: Context, // Contexto para enviar notificações
) {
    // Estado para notificações e animações
    val isNotificationsEnabled = settingsViewModel.isNotificationsEnabled.collectAsState()
    val areAnimationsEnabled = settingsViewModel.areAnimationsEnabled.collectAsState()

    // Lógica para definir o tema automaticamente
    var selectedTheme by remember { mutableStateOf("Automático") }

    // Calcular automaticamente o tema baseado na hora
    val isDarkTheme = remember { mutableStateOf(settingsViewModel.getCurrentThemeMode()) }

    // Mudança de tema automática baseada no horário
    LaunchedEffect(isDarkTheme.value) {
        if (selectedTheme == "Automático") {
            isDarkTheme.value = settingsViewModel.getCurrentThemeMode()
        }
    }

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
            onCheckedChange = { enabled ->
                settingsViewModel.setNotificationsEnabled(enabled) // Atualiza o estado
                if (enabled) {
                    // Criar canal de notificação e enviar
                    createNotificationChannel(context)
                    sendNotification(context, "Notificações ativadas!")
                }
            }
        )

        Spacer(modifier = Modifier.size(6.dp))

        // Opção de Tema
        Text("Tema", style = MaterialTheme.typography.bodyLarge, fontSize = 18.sp)
        Spacer(modifier = Modifier.size(6.dp))

        // Opção de seleção do tema
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedTheme = "Claro"
                        onThemeChange("Claro") // Passa a mudança de tema para a ViewModel
                    },
                horizontalArrangement = Arrangement.Start, // Alinha os itens à esquerda
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedTheme == "Claro",
                    onClick = {
                        selectedTheme = "Claro"
                        onThemeChange("Claro") // Passa a mudança de tema para a ViewModel
                    }
                )
                Text("Claro", modifier = Modifier.padding(start = 8.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedTheme = "Escuro"
                        onThemeChange("Escuro") // Passa a mudança de tema para a ViewModel
                    },
                horizontalArrangement = Arrangement.Start, // Alinha os itens à esquerda
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedTheme == "Escuro",
                    onClick = {
                        selectedTheme = "Escuro"
                        onThemeChange("Escuro") // Passa a mudança de tema para a ViewModel
                    }
                )
                Text("Escuro", modifier = Modifier.padding(start = 8.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedTheme = "Automático"
                        onThemeChange("Automático") // Passa a mudança de tema para a ViewModel
                    },
                horizontalArrangement = Arrangement.Start, // Alinha os itens à esquerda
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedTheme == "Automático",
                    onClick = {
                        selectedTheme = "Automático"
                        onThemeChange("Automático") // Passa a mudança de tema para a ViewModel
                    }
                )
                Text("Automático", modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.size(6.dp))

        // Opção de Animações Visuais
        RowOption(
            title = "Animações Visuais",
            description = "Habilitar ou desabilitar animações visuais",
            isChecked = areAnimationsEnabled.value,
            onCheckedChange = { enabled ->
                settingsViewModel.setAnimationsEnabled(enabled)
            }
        )
    }

    // Altera o tema da aplicação com base na seleção
    LaunchedEffect(selectedTheme) {
        // Atualize a configuração do tema com base na seleção
        when (selectedTheme) {
            "Escuro" -> settingsViewModel.setDarkModeEnabled(true)
            "Claro" -> settingsViewModel.setDarkModeEnabled(false)
            "Automático" -> settingsViewModel.setDarkModeEnabled(isDarkTheme.value) // Configura de acordo com o sistema
        }
    }
}


@Composable
fun RowOption(
    title: String,
    description: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
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
