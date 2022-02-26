package dev.yjyoon.coverist

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
import dev.yjyoon.coverist.BookInfoInput.Companion.bookInfoInputQuestions

@Composable
fun BookInfoInputScreen(navController: NavController, viewModel: BookInfoInputViewModel) {
    val (step, setStep) = remember { mutableStateOf(0) }
    val question = bookInfoInputQuestions[step]
    val maxStep = bookInfoInputQuestions.size

    BackHandler {
        if (step > 0) setStep(step - 1)
        else {
            navController.navigate("title") {
                popUpTo("title") { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            BookInfoInputTopAppBar(step, maxStep)
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
                enabledNext = viewModel.isValidateInput(step),
                onNextClick = { setStep(step + 1) },
                showDone = step + 1 == maxStep,
                onDoneClick = {}
            )
        }
    )
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
    viewModel: BookInfoInputViewModel,
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
                    val genres: List<String> by viewModel.loadGenres()
                        .observeAsState(initial = List(28) { " " })

                    GenreGrid(
                        genres = genres,
                        selected = viewModel.bookGenre,
                        onChange = viewModel::editGenre
                    )
                }
                BookInfoInput.Type.SubGenre -> {
                    val subGenres: List<String> by viewModel.loadSubGenres(viewModel.bookGenre)
                        .observeAsState(initial = List(28) { " " })

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
                        onDelete = viewModel::deleteTag
                    )
                }
                else -> {}
            }
        }
    }
}

@Composable
fun Loading() {
    CircularProgressIndicator(modifier = Modifier.padding(144.dp))
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
fun QuitButton() {
    IconButton(
        onClick = { },
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
fun BookInfoInputTopAppBar(step: Int, maxStep: Int) {
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
            QuitButton()
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