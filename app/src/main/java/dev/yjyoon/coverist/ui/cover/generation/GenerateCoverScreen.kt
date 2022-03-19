package dev.yjyoon.coverist.ui.cover.generation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dev.yjyoon.coverist.ui.cover.generation.done.ShowCoverScreen
import dev.yjyoon.coverist.ui.cover.generation.input.BookInfoInputScreen
import dev.yjyoon.coverist.ui.cover.generation.loading.GeneratingScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GenerateCoverScreen(
    navController: NavController,
    viewModel: GenerateCoverViewModel
) {
    Crossfade(
        targetState = viewModel.uiState,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearEasing
        )
    ) { targetUiState ->
        when (targetUiState) {
            UiState.Input -> {
                BookInfoInputScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            UiState.Generate -> {
                GeneratingScreen()
            }
            UiState.Show -> {
                ShowCoverScreen(
                    navController = navController,
                    coverUrls = viewModel.covers.map { it.url }
                )
            }
            UiState.Error -> {

            }
        }
    }

}