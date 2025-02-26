package com.example.alivia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alivia.R
import com.example.alivia.ui.components.customTextStyle
import com.example.alivia.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val user by authViewModel.user.observeAsState()
    val errorMessage by authViewModel.errorMessage.observeAsState()
    val isLoading by authViewModel.isLoading.observeAsState(initial = false)

    // Navega para a tela principal se o usuário estiver autenticado
    LaunchedEffect(user) {
        if (user != null) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "alivia",
            style = customTextStyle
        )
        Text(
            text = "Entrar",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { authViewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Não possui uma conta? Cadastre-se",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { navController.navigate("signup") }
        )
        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val user by authViewModel.user.observeAsState()
    val errorMessage by authViewModel.errorMessage.observeAsState()
    val isLoading by authViewModel.isLoading.observeAsState(initial = false)

    // Navega para a tela principal se o usuário estiver autenticado
    LaunchedEffect(user) {
        if (user != null) {
            navController.navigate("home") {
                popUpTo("signup") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "alivia",
            style = customTextStyle
        )
        Text(
            text = "Cadastrar",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Senha") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (password == confirmPassword) {
                    authViewModel.signUp(email, password, name)
                }
                // Opcional: caso as senhas não correspondam, exiba uma mensagem de erro
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cadastrar")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Já possui uma conta? Entrar",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { navController.navigate("login") }
        )
        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error)
        }
    }
}
