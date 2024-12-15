package com.example.alivia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.alivia.models.Stretching
import com.example.alivia.models.stretchingList
import com.example.alivia.ui.components.StretchingListItem

@ExperimentalMaterial3Api
@Composable
fun FavoritesScreen(
    onStretchingSelected: (Stretching) -> Unit,
    onFavoriteToggle: (Stretching) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Favoritos",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { innerPadding ->
        val favoriteStretching = stretchingList.filter { it.isFavorite }

        if (favoriteStretching.isEmpty()) {
            // Exibe o texto padrão quando não há favoritos
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Você ainda não adicionou favoritos.",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // Exibe a lista de favoritos
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp)
            ) {
                items(favoriteStretching) { stretching ->
                    StretchingListItem(
                        stretching = stretching,
                        onStretchingSelected = { onStretchingSelected(it) },
                        onFavoriteToggle = { onFavoriteToggle(it) }
                    )
                }
            }
        }
    }
}