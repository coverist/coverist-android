package dev.yjyoon.coverist.ui.oss

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.yjyoon.coverist.ui.theme.CoveristTheme

@Composable
fun OssScreen() {
    val uriHandler = LocalUriHandler.current

    Scaffold {
        LazyColumn(modifier = Modifier.padding(18.dp)) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.primary)
                        .padding(18.dp)
                ) {
                    Text(
                        "OSS Notice | Coverist-Android",
                        style = MaterialTheme.typography.h6.copy(color = Color.White)
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
            items(Oss.list) { oss ->
                OssTile(
                    uriHandler = uriHandler,
                    name = oss.name,
                    url = oss.url,
                    copyright = oss.copyright,
                    ossType = oss.type
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OssTile(
    uriHandler: UriHandler,
    name: String,
    url: String,
    copyright: String,
    ossType: Oss.Type
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(name, style = MaterialTheme.typography.h6)
        Text(
            url,
            style = MaterialTheme.typography.body1.copy(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.clickable { uriHandler.openUri(url) }
        )
        Text("Copyright ⓒ $copyright All rights reserved.")
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    shape = MaterialTheme.shapes.small
                )
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            AnimatedContent(targetState = expanded) { targetExpanded ->
                Text(
                    if (targetExpanded) Oss.description[ossType]!! else Oss.name[ossType]!!,
                    modifier = Modifier.align(
                        Alignment.TopStart
                    )
                )
            }
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .then(Modifier.size(24.dp))
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    if (expanded) Icons.Rounded.Remove else Icons.Rounded.Add,
                    contentDescription = null
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Divider()
    }
}

@Preview
@Composable
fun OssScreenPreview() {
    CoveristTheme {
        OssScreen()
    }
}