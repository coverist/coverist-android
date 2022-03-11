package dev.yjyoon.coverist.ui.cover.generation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import dev.yjyoon.coverist.data.remote.model.Cover
import dev.yjyoon.coverist.ui.cover.generation.done.ShowCoverScreen
import dev.yjyoon.coverist.ui.cover.generation.input.BookInfoInputScreen
import dev.yjyoon.coverist.ui.cover.generation.loading.GeneratingScreen

@Composable
fun GenerateCoverScreen(
    navController: NavController,
    viewModel: GenerateCoverViewModel
) {
    if (viewModel.isGenerating) {
        val covers: List<Cover>? by viewModel.generateCover(LocalContext.current)
            .observeAsState(initial = null)

        if (covers == null) GeneratingScreen()
        else {
            ShowCoverScreen(
                navController = navController,
                coverUrls = covers!!.map { it.url }
            )
        }
    } else {
        BookInfoInputScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
}