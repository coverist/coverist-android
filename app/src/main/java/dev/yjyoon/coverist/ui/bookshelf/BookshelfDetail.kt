package dev.yjyoon.coverist.ui.bookshelf

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dev.yjyoon.coverist.ui.common.CommonLoading
import dev.yjyoon.coverist.ui.common.SimpleFlowRow
import dev.yjyoon.coverist.ui.cover.generation.GenerateCoverUiState
import dev.yjyoon.coverist.ui.cover.generation.done.ShowCoverScreen
import dev.yjyoon.coverist.ui.cover.generation.input.TagChip
import dev.yjyoon.coverist.ui.cover.generation.loading.GeneratingScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookShelfDetail(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BookShelfViewModel
) {
    Crossfade(
        targetState = viewModel.uiState,
        animationSpec = tween(
            durationMillis = 100,
            easing = LinearEasing
        )
    ) { targetUiState ->
        when (targetUiState) {
            GenerateCoverUiState.Waiting -> {
                val configuration = LocalConfiguration.current
                val scaffoldState = rememberBottomSheetScaffoldState()

                val book = viewModel.selectedBook
                val coverIndex = viewModel.selectedCoverIndex

                if (book != null) {
                    BottomSheetScaffold(
                        sheetShape = MaterialTheme.shapes.large.copy(
                            bottomStart = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp)
                        ),
                        sheetPeekHeight = (configuration.screenHeightDp / 1.95).dp,
                        scaffoldState = scaffoldState,
                        sheetContent = {
                            Column {
                                BookInfo(
                                    title = book.title,
                                    author = book.author,
                                    genre = book.genre,
                                    subGenre = book.subGenre,
                                    tags = book.tags
                                )
                                Divider()
                                Spacer(Modifier.height(4.dp))
                                OtherCovers(
                                    coverUrls = book.coverUrls,
                                    onClick = viewModel::selectCoverIndex
                                )
                                Divider(Modifier.padding(vertical = 8.dp))
                                GenerateButton(onClick = { viewModel.addCover(book.bookId) })
                            }
                        }
                    ) {
                        CoverGraphic(
                            configuration = configuration,
                            coverUrl = book.coverUrls[coverIndex]
                        )
                    }
                } else {
                    CommonLoading(modifier = Modifier.fillMaxSize())
                }
            }
            GenerateCoverUiState.Generating -> {
                GeneratingScreen()
            }
            GenerateCoverUiState.Done -> {
                ShowCoverScreen(
                    navController = navController,
                    coverUrls = viewModel.newCovers.map { it.url },
                    onConfirm = viewModel::confirmGeneration
                )
            }
            GenerateCoverUiState.Fail -> {
                //TODO: Fail to generate cover.
            }
        }
    }
}

@Composable
fun CoverGraphic(
    configuration: Configuration,
    coverUrl: String
) {
    Crossfade(
        targetState = coverUrl,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearEasing
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = it,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(
                    color = Color.Black.copy(alpha = 0.25f),
                    blendMode = BlendMode.Darken
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height((configuration.screenWidthDp * 1.1).dp)
                    .blur(32.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(configuration.screenWidthDp.dp)
            ) {
                Card(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        modifier = Modifier.width((configuration.screenWidthDp / 2).dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BookInfo(
    title: String,
    author: String,
    genre: String,
    subGenre: String,
    tags: List<String>
) {
    Column(
        modifier = Modifier.padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text("$author 저", color = Color.DarkGray)
        Text("$genre | $subGenre", color = Color.Gray)
        Spacer(Modifier.height(2.dp))
        SimpleFlowRow(
            verticalGap = 6.dp,
            horizontalGap = 4.dp,
            modifier = Modifier.padding(vertical = 2.dp)
        ) {
            tags.map {
                TagChip(
                    tag = it,
                    color = MaterialTheme.colors.primary,
                    textStyle = MaterialTheme.typography.caption,
                    contentPadding = PaddingValues(
                        horizontal = 8.dp,
                        vertical = 6.dp
                    ),
                    deletable = false
                )
            }
        }
    }
}

@Composable
fun OtherCovers(
    coverUrls: List<String>,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            "이 책의 다른 표지",
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            items(coverUrls.size) { index ->
                Card(
                    shape = RoundedCornerShape(4.dp),
                    elevation = 2.dp
                ) {
                    AsyncImage(
                        model = coverUrls[index],
                        contentDescription = null,
                        modifier = Modifier
                            .width(108.dp)
                            .clickable { onClick(index) },
                    )
                }
            }
        }
    }
}

@Composable
fun GenerateButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth(),
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp)
    ) {
        Text(
            "이 책으로 새로운 표지 만들기",
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
    }
}