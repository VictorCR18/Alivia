package com.example.alivia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alivia.models.Stretching
import com.example.alivia.models.stretchingList
import com.example.alivia.ui.components.StretchingListItem
import com.example.alivia.ui.components.TopAppBarWithMenu

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    onStretchingSelected: (Stretching) -> Unit,
    onSettingsClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredStretchings = remember(searchQuery) {
        stretchingList.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    val recentSearches = remember { mutableStateListOf<Stretching>() }

    Scaffold(
        topBar = {
            TopAppBarWithMenu(
                onSettingsClick = onSettingsClick,
                onHelpClick = onHelpClick
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Barra de pesquisa
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Pesquisar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            // LazyRow para buscas recentes
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recentSearches) { stretching ->
                    Button(onClick = { onStretchingSelected(stretching) }) {
                        Text(stretching.name)
                    }
                }
            }

            // LazyColumn para lista de planetas filtrados
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                items(filteredStretchings) { stretching ->
                    StretchingListItem(
                        stretching = stretching,
                        onStretchingSelected = { selectedStretching ->
                            if (!recentSearches.contains(selectedStretching)) {
                                recentSearches.add(0, selectedStretching)
                            }
                            onStretchingSelected(selectedStretching)
                        },
                        onFavoriteToggle = { favoriteStretching ->
                            favoriteStretching.isFavorite = !favoriteStretching.isFavorite
                        }
                    )
                }
            }
        }
    }
}
