package dev.yjyoon.coverist.ui.cover.generation.done

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.yjyoon.coverist.ui.theme.CoveristTheme
import dev.yjyoon.coverist.util.HorizontalImageCarousel

@Composable
fun ShowCoverScreen(
    navController: NavController,
    coverUrls: List<String>
) {

    BackHandler {
        navController.navigate("title") {
            popUpTo("show-cover") { inclusive = true }
        }
    }

    Scaffold(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        bottomBar = {
            ConfirmButton(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            ShowCoverTitle()
            ShowCoverContent(coverUrls)
        }
    }
}

@Composable
fun ShowCoverTitle(modifier: Modifier = Modifier) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = Color.Black.copy(alpha = 0.12f),
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
    ) {
        Text(
            "새로운 표지가 탄생했어요!",
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 18.dp)
        )
    }
}

@Composable
fun ConfirmButton(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
    ) {
        Button(
            onClick = {
                navController.navigate("title") {
                    popUpTo("show-cover") { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.onPrimary,
                contentColor = MaterialTheme.colors.primary
            ),
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("완료", style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Composable
fun ShowCoverContent(
    coverUrls: List<String>,
    modifier: Modifier = Modifier) {
    HorizontalImageCarousel(
        imageUrls = coverUrls,
        aspectRatio = 3 / 4f
    )
}

@Composable
fun CoverImage(
    text: String
) {
    Surface(
        color = Color.Yellow
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text)
        }
    }
}

@Preview
@Composable
fun ShowCoverScreenPreview() {
    CoveristTheme {
    }
}