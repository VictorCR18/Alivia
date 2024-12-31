package com.example.alivia.ui.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.alivia.R

@Composable
fun ProfileScreen(navController: NavHostController) {
    // Contexto e estado da imagem
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var userName by remember { mutableStateOf(TextFieldValue("Nome de Exemplo")) }

    // Função para abrir a galeria e selecionar uma imagem
    val openGallery = { activity: Activity ->
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(intent, 100)
    }

    // Função para processar a imagem retornada da galeria
    val handleImageResult = { resultUri: Uri? ->
        resultUri?.let { uri ->
            imageUri = uri
        } ?: run {
            Toast.makeText(context, "Imagem não selecionada", Toast.LENGTH_SHORT).show()
        }
    }

    // Tela de perfil
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Foto de perfil
        Box(
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
        ) {
            // Exibindo a imagem do perfil
            val painter = rememberAsyncImagePainter(
                imageUri ?: R.drawable.perfil // Imagem padrão caso não haja seleção
            )
            Image(
                painter = painter,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )

            // Botão para alterar a foto de perfil
            IconButton(
                onClick = {
                    openGallery(context as Activity)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar Foto",
                    tint = Color.White
                )
            }
        }

        // Nome do usuário
        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para salvar alterações
        Button(
            onClick = {
                // Ação para salvar nome e imagem de perfil
                Toast.makeText(context, "Perfil salvo!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Salvar Alterações")
        }
    }
}


//// Para manipular o resultado da galeria
//fun Activity.onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
//        val selectedImageUri: Uri? = data?.data
//        val handleImageResult = {
//            selectedImageUri?.let { uri ->
//                imageUri = uri
//            }
//        }
//    }
//}
