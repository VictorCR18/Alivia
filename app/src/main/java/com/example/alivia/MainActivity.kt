package com.example.alivia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alivia.ui.components.BottomNavigationBar
import com.example.alivia.ui.components.DrawerContent
import com.example.alivia.ui.components.TopBar
import com.example.alivia.ui.screens.ExerciseExampleScreen
import com.example.alivia.ui.screens.FavoritesScreen
import com.example.alivia.ui.screens.HelpAndSupportScreen
import com.example.alivia.ui.screens.HomeScreen
import com.example.alivia.ui.screens.ProfileScreen
import com.example.alivia.ui.screens.SettingsScreen
import com.example.alivia.ui.screens.TrainingDetailsScreen
import com.example.alivia.ui.theme.AliviaTheme
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val isDarkTheme = remember { mutableStateOf(false) }
            val isLoading = remember { mutableStateOf(false) }
            val delayTime = remember { mutableStateOf(500L) }

            // Navegação para ativar/desativar o spinner
            LaunchedEffect(navController) {
                var previousRoute: String? = null // Rota de origem

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    val currentRoute = destination.route

                    // Verifica se a navegação é para a mesma rota
                    if (previousRoute == currentRoute) {
                        isLoading.value = false // Não ativa o spinner
                        return@addOnDestinationChangedListener
                    }

                    // Configura o tempo de delay com base na rota de origem
                    delayTime.value = when {
                        previousRoute?.startsWith("exerciseDetails") == true -> 1000L // Delay maior ao sair do vídeo
                        previousRoute?.startsWith("home") == true -> 0L // Sem delay ao sair de home
                        previousRoute?.startsWith("favorites") == true -> 0L
                        else -> 500L // Delay padrão
                    }

                    // Atualiza a rota de origem
                    previousRoute = currentRoute

                    // Ativa o spinner e aplica o delay configurado
                    isLoading.value = true
                    scope.launch {
                        kotlinx.coroutines.delay(delayTime.value)
                        isLoading.value = false
                    }
                }
            }


            AliviaTheme(darkTheme = isDarkTheme.value) {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = true,
                    drawerContent = {
                        DrawerContent(
                            navController = navController,
                            drawerState = drawerState,
                            scope = scope
                        )
                    },
                    content = {
                        Scaffold(
                            topBar = {
                                TopBar(
                                    onOpenDrawer = { scope.launch { drawerState.open() } }
                                )
                            },
                            bottomBar = { BottomNavigationBar(navController) }
                        ) { innerPadding ->
                            // Conteúdo principal
                            Box(modifier = Modifier.fillMaxSize()) {
                                // NavHost gerencia as rotas
                                NavHost(
                                    navController = navController,
                                    startDestination = "home",
                                    modifier = Modifier.padding(innerPadding)
                                ) {
                                    composable("home") {
                                        HomeScreen(
                                            navController,
                                            context = LocalContext.current
                                        )
                                    }
                                    composable("settings") {
                                        SettingsScreen(
                                            navController = navController,
                                            onThemeToggle = {
                                                isDarkTheme.value = !isDarkTheme.value
                                            },
                                            isDarkThemeEnabled = isDarkTheme.value,
                                            context = LocalContext.current
                                        )
                                    }
                                    composable("favorites") { FavoritesScreen(navController) }
                                    composable("trainingDetails/{eventId}") { backStackEntry ->
                                        val trainingId =
                                            backStackEntry.arguments?.getString("eventId")
                                        val context = LocalContext.current
                                        TrainingDetailsScreen(
                                            trainingId = trainingId,
                                            context = context,
                                            navController = navController
                                        )
                                    }
                                    composable("exerciseDetails/{exerciseId}") { backStackEntry ->
                                        val exerciseId =
                                            backStackEntry.arguments?.getString("exerciseId")
                                        ExerciseExampleScreen(
                                            exerciseId = exerciseId,
                                            isNotificationsEnabled = true
                                        )
                                    }
                                    composable("profile") {
                                        ProfileScreen(navController)
                                    }
                                    composable("helpAndSupport") {
                                        HelpAndSupportScreen(navController)
                                    }
                                }

                                // Spinner de carregamento
                                if (isLoading.value) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color(0xFFF5F8ff)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(color = Color(0xFF267A9C))
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
