package dev.yjyoon.coverist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TitleScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Logo()
            StartButton(navController = navController)
        }
    }
}

@Composable
fun Logo() {
    Text(text = "Coverist", style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold))
}

@Composable
fun StartButton(navController: NavController) {
    Button(
        onClick = { navController.navigate("book-info-input") },
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text("새로운 표지 만들기", style = MaterialTheme.typography.h6)
    }
}