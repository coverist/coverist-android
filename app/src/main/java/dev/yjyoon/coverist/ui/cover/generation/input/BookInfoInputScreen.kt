package dev.yjyoon.coverist.ui.cover.generation.input

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.yjyoon.coverist.R
import dev.yjyoon.coverist.data.remote.model.Genre
import dev.yjyoon.coverist.ui.cover.generation.GenerateCoverViewModel
import dev.yjyoon.coverist.ui.cover.generation.input.BookInfoInput.Companion.bookInfoInputQuestions
import dev.yjyoon.coverist.ui.cover.generation.done.ShowCoverScreen
import dev.yjyoon.coverist.ui.cover.generation.loading.GeneratingScreen
import dev.yjyoon.coverist.util.QuestionDialog
import kotlinx.coroutines.coroutineScope

@Composable
fun BookInfoInputScreen(
    navController: NavController,
    viewModel: GenerateCoverViewModel
) {
    val (step, setStep) = remember { mutableStateOf(0) }
    var showCloseDialog by remember { mutableStateOf(false) }

    val question = bookInfoInputQuestions[step]
    val maxStep = bookInfoInputQuestions.size

    BackHandler {
        if (step > 0) setStep(step - 1)
        else {
            showCloseDialog = true
        }
    }

    Scaffold(
        topBar = {
            BookInfoInputTopAppBar(
                step = step,
                maxStep = maxStep,
                onClose = { showCloseDialog = true }
            )
        },
        content = { innerPadding ->
            InputContent(
                viewModel = viewModel,
                question = question,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        },
        bottomBar = {
            BookInfoInputBottomBar(
                showPrevious = step > 0,
                onPreviousClick = { setStep(step - 1) },
                enabledNext = viewModel.isValidInput(step),
                onNextClick = { setStep(step + 1) },
                showDone = step + 1 == maxStep,
                onDoneClick = { viewModel.isGenerating = true }
            )
        }
    )

    if (showCloseDialog) {
        QuestionDialog(
            title = stringResource(R.string.app_name),
            question = stringResource(R.string.close_book_info_input),
            onYes = {
                showCloseDialog = false
                navController.navigate("title") {
                    popUpTo("title") { inclusive = true }
                }
            },
            onNo = { showCloseDialog = false },
            onDismissRequest = { showCloseDialog = false }
        )
    }
}

@Composable
fun QuestionTextBox(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.04f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(20.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun InputContent(
    viewModel: GenerateCoverViewModel,
    question: BookInfoInput.Question,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.padding(18.dp)
    ) {
        QuestionTextBox(text = stringResource(id = question.questionText))
        Surface(
            modifier = Modifier
                .padding(top = 22.dp, bottom = 4.dp)
        ) {
            when (question.inputType) {
                BookInfoInput.Type.TitleAndAuthor -> {
                    TitleAndAuthorInput(
                        title = viewModel.bookTitle,
                        author = viewModel.bookAuthor,
                        onEditTitle = viewModel::editTitle,
                        onEditAuthor = viewModel::editAuthor
                    )
                }
                BookInfoInput.Type.Genre -> {
                    val genres: List<Genre> by viewModel.loadGenres()
                        .observeAsState(initial = List(28) { Genre(0, "") })

                    GenreGrid(
                        genres = genres,
                        selected = viewModel.bookGenre,
                        onChange = viewModel::editGenre
                    )
                }
                BookInfoInput.Type.SubGenre -> {
                    val subGenres: List<Genre> by viewModel.loadSubGenres()
                        .observeAsState(initial = List(28) { Genre(0, "") })

                    GenreGrid(
                        genres = subGenres,
                        selected = viewModel.bookSubGenre,
                        onChange = viewModel::editSubGenre
                    )
                }
                BookInfoInput.Type.Tags -> {
                    TagsInput(
                        tags = viewModel.bookTags,
                        onAdd = viewModel::addTag,
                        enableAdd = viewModel.isNotFullTags(),
                        onDelete = viewModel::deleteTag,
                        isInvalid = viewModel::isInvalidTag
                    )
                }
                BookInfoInput.Type.Publisher -> {
                    UploadPublisherImage(
                        imageUri = viewModel.bookPublisher,
                        onUpload = viewModel::editPublisher,
                        onDelete = viewModel::deletePublisher,
                        setEmpty = viewModel::setPublisherEmpty
                    )
                }
                else -> {}
            }
        }
    }
}

@Composable
fun TopAppBarTitle(step: Int, maxStep: Int, modifier: Modifier = Modifier) {
    Text("${step + 1} / $maxStep", modifier = modifier.alpha(ContentAlpha.medium))
}

@Composable
fun StepProgressBar(step: Int, maxStep: Int) {
    val animatedProgress by animateFloatAsState(
        targetValue = (step + 1) / maxStep.toFloat(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    LinearProgressIndicator(
        progress = animatedProgress,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        backgroundColor = Color.Black.copy(alpha = 0.12f)
    )
}

@Composable
fun QuitButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        Icon(
            Icons.Filled.Close,
            contentDescription = "Close",
            modifier = Modifier.alpha(ContentAlpha.medium)
        )
    }
}

@Composable
fun BookInfoInputTopAppBar(
    step: Int,
    maxStep: Int,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background)
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                enabled = false,
                onClick = { },
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close",
                    modifier = Modifier.alpha(0f)
                )
            }
            TopAppBarTitle(
                step = step,
                maxStep = maxStep,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            QuitButton(
                onClick = onClose
            )
        }
        StepProgressBar(step = step, maxStep = maxStep)
    }
}

@Composable
fun BookInfoInputBottomBar(
    showPrevious: Boolean,
    onPreviousClick: () -> Unit,
    enabledNext: Boolean,
    onNextClick: () -> Unit,
    showDone: Boolean,
    onDoneClick: () -> Unit
) {
    Surface(
        elevation = 7.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            if (showPrevious) {
                OutlinedButton(
                    onClick = onPreviousClick,
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("이전", style = MaterialTheme.typography.subtitle1)
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
            Button(
                onClick = if (showDone) onDoneClick else onNextClick,
                enabled = enabledNext,
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (showDone) "완료" else "다음", style = MaterialTheme.typography.subtitle1)
            }
        }
    }
}

@Preview
@Composable
fun BookInfoInputPreview() {
}