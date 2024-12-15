package com.example.alivia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.alivia.navigation.NavGraph
import com.example.alivia.ui.theme.AliviaTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavGraph(
                onSettingsClick = {
                    // Ação para Configurações (pode abrir uma nova tela ou exibir um diálogo)
                },
                onHelpClick = {
                    // Ação para Ajuda (pode abrir uma nova tela ou exibir um diálogo)
                }
            )
        }
    }
}