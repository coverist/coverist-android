package dev.yjyoon.coverist.ui.cover.generation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dev.yjyoon.coverist.ui.cover.generation.done.ShowCoverScreen
import dev.yjyoon.coverist.ui.cover.generation.input.BookInfoInputScreen
import dev.yjyoon.coverist.ui.cover.generation.loading.GeneratingScreen

@Composable
fun GenerateCoverScreen(
    navController: NavController,
    viewModel: GenerateCoverViewModel
) {
    when (viewModel.uiState) {
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
                coverUrls = viewModel.covers.value!!.map { it.url }
            )
        }
        UiState.Error -> {

        }
    }
}