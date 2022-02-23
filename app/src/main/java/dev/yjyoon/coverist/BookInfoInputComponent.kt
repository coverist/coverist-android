package dev.yjyoon.coverist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TitleAndAuthorInput(
    title: String,
    author: String,
    onEditTitle: (String) -> Unit,
    onEditAuthor: (String) -> Unit
) {
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
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = author,
            onValueChange = onEditAuthor,
            label = { Text("저자") },
            singleLine = true,
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

@Preview(showBackground = true)
@Composable
fun TitleAndAuthorInputPreview() {
    TitleAndAuthorInput(title = "ㅎㅇ", author = "ㅎㅇ", onEditTitle = {}, onEditAuthor = {})
}