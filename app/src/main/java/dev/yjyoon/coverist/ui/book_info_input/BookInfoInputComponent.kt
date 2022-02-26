package dev.yjyoon.coverist.ui.book_info_input

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Tag
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.yjyoon.coverist.util.SimpleFlowRow
import dev.yjyoon.coverist.util.TextInputDialog

@Composable
fun TitleAndAuthorInput(
    title: String,
    author: String,
    onEditTitle: (String) -> Unit,
    onEditAuthor: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onEditTitle,
            label = { Text("제목") },
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = author,
            onValueChange = onEditAuthor,
            label = { Text("저자") },
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenreGrid(
    genres: List<String>,
    selected: String,
    onChange: (String) -> Unit
) {
    val (genre, setGenre) = remember { mutableStateOf(selected) }

    LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 128.dp)) {
        items(genres.size) {
            if (genres[it] == genre)
                Button(
                    onClick = {
                        setGenre(genres[it])
                        onChange(genres[it])
                    },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Text(genres[it])
                }
            else
                OutlinedButton(
                    onClick = {
                        setGenre(genres[it])
                        onChange(genres[it])
                    },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Text(genres[it])
                }
        }
    }
}

@Composable
fun TagsInput(
    tags: List<String>,
    onAdd: (String) -> Unit,
    enableAdd: Boolean,
    onDelete: (String) -> Unit,
    isInvalid: (String) -> Boolean
) {
    var showDialog by remember { mutableStateOf(false) }

    SimpleFlowRow(
        verticalGap = 8.dp,
        horizontalGap = 8.dp,
        alignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        for (tag in tags) {
            TagChip(tag, onDelete)
        }

        TagAdd(
            enabled = enableAdd,
            onClick = { showDialog = true }
        )
    }

    if (showDialog) {
        TextInputDialog(
            onDismissRequest = { showDialog = false },
            textFieldLabel = { Text("태그 입력") },
            textFieldLeadingIcon = { Icon(Icons.Rounded.Tag, "Tag") },
            submitButtonText = "추가",
            onSubmit = onAdd,
            isTextFieldError = isInvalid
        )
    }
}

@Composable
fun TagAdd(
    enabled: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        enabled = enabled,
        onClick = onClick,
        modifier = Modifier
            .width(36.dp)
            .height(36.dp)
    ) {
        Icon(
            Icons.Rounded.Add,
            "Add tag.",
            modifier = Modifier
                .background(
                    color = if (enabled) MaterialTheme.colors.primary
                            else MaterialTheme.colors.onSurface
                                .copy(alpha = 0.12f)
                                .compositeOver(MaterialTheme.colors.surface),
                    shape = CircleShape
                )
                .padding(8.dp),
            tint = if (enabled) MaterialTheme.colors.onPrimary
                    else MaterialTheme.colors.onSurface
                        .copy(alpha = ContentAlpha.disabled)
        )
    }
}

@Composable
fun TagChip(
    tag: String,
    onDelete: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary.copy(alpha = 0.12f), CircleShape)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "#$tag",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.primary.copy(alpha = 0.87f),
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(
            onClick = { onDelete(tag) },
            modifier = Modifier
                .width(16.dp)
                .height(16.dp)
        ) {
            Icon(
                Icons.Default.Close,
                "Delete tag.",
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.2f),
                        shape = CircleShape
                    )
                    .padding(2.dp),
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TagsRowPreview() {
}