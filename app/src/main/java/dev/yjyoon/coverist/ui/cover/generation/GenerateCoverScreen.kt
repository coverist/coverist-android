package dev.yjyoon.coverist.ui.cover.generation

import androidx.compose.runtime.*
import androidx.navigation.NavController
import dev.yjyoon.coverist.ui.cover.generation.done.ShowCoverScreen
import dev.yjyoon.coverist.ui.cover.generation.input.BookInfoInputScreen
import dev.yjyoon.coverist.ui.cover.generation.loading.GeneratingScreen

@Composable
fun GenerateCoverScreen(
    navController: NavController,
    viewModel: GenerateCoverViewModel
) {

    if(viewModel.covers == null || viewModel.covers!!.isEmpty()) {
        if(viewModel.isGenerating) GeneratingScreen()
        else BookInfoInputScreen(navController = navController, viewModel = viewModel)
    } else {
        ShowCoverScreen(
            navController = navController,
            coverUrls = viewModel.covers!!.map { it.url }
        )
    }
}