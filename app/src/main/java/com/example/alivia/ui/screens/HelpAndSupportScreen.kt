package com.example.alivia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HelpAndSupportScreen(navController: NavHostController) {
    // Lista de FAQs
    val faqs = listOf(
        "Como alterar meu tema para claro/escuro?" to "Acesse Configurações > Tema e alterne entre as opções disponíveis.",
        "O que fazer se o aplicativo não abrir?" to "Tente reiniciar o dispositivo ou reinstalar o aplicativo.",
        "Posso entrar em contato com o suporte diretamente pelo aplicativo?" to "Sim, utilize o botão abaixo para nos enviar uma mensagem."
    )

    // Estado para entrada de mensagens
    var message by remember { mutableStateOf("") }
    var confirmationMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        // Título
        Text(
            text = "Ajuda e Suporte",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de FAQs
        Text(
            text = "Perguntas Frequentes",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(faqs.size) { index ->
                FAQItem(question = faqs[index].first, answer = faqs[index].second)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Caixa de entrada de mensagens
        Text(
            text = "Envie uma mensagem para o suporte",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Caixa de mensagem personalizada
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.medium // Bordas arredondadas
                )
                .border(
                    width = 2.dp, // Espessura da borda
                    color = Color(0xFF267A9C), // Cor da borda
                    shape = MaterialTheme.shapes.medium // Mesma forma para combinar com o fundo
                )
                .padding(horizontal = 8.dp),
        ) {
            if (message.isEmpty()) {
                Text(
                    text = "Digite sua mensagem aqui...",
                    color = Color(0xFF267A9C).copy(alpha = 0.5f),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            BasicTextField(
                value = message,
                onValueChange = { message = it },
                textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botão de envio
        Button(
            onClick = {
                confirmationMessage = "Mensagem enviada: $message"
                message = ""
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF267A9C),
                contentColor = Color.White
            )        ) {
            Text(text = "Enviar Mensagem")
        }

        // Mensagem de confirmação
        confirmationMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                color = Color(0xFF267A9C),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun FAQItem(question: String, answer: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Q: $question",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "A: $answer",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}
