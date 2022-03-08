package dev.yjyoon.coverist

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TitleScreen(navController: NavController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("cover-generation") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            ) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(innerPadding)
        ) {
            Logo()
        }
    }
}

@Composable
fun Logo() {
}