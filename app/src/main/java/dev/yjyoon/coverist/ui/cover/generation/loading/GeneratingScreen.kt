package dev.yjyoon.coverist.ui.cover.generation.loading

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.yjyoon.coverist.ui.theme.CoveristTheme

@Composable
fun GeneratingScreen() {
    val configuration = LocalConfiguration.current
    Scaffold(
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 36.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width((configuration.screenWidthDp / 2.5).dp)
                    .height((configuration.screenWidthDp / 2.5).dp)
                    .align(Alignment.Center),
                color = Color.White,
                strokeWidth = 24.dp
            )
            Text(
                "표지를 생성 중입니다\n잠시만 기다려주세요!",
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview
@Composable
fun GeneratingScreenPreview() {
    CoveristTheme {
        GeneratingScreen()
    }
}