package dev.yjyoon.coverist.ui.title

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.yjyoon.coverist.R
import dev.yjyoon.coverist.ui.theme.CoveristTheme
import kotlinx.coroutines.launch

@Composable
fun TitleScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {

        },
        drawerShape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp)
        ),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            ) {
                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }) {
                    Icon(
                        Icons.Rounded.Menu,
                        contentDescription = null
                    )
                }
            }
        },
        bottomBar = {
            StartButton(onClick = {
                navController.navigate("cover-generation")
            })
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Graphic()
            About()
        }
    }
}

@Composable
fun Graphic(modifier: Modifier = Modifier) {
    val imageAssetId = R.drawable.ic_undraw_reading_time_re_phf7

    Image(
        painter = painterResource(id = imageAssetId),
        contentDescription = null
    )
}

@Composable
fun About(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Text(
            stringResource(id = R.string.coverist),
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(id = R.string.long_text),
            style = MaterialTheme.typography.caption, textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun StartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(24.dp)
    ) {
        Button(
            onClick = onClick,
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
            modifier = Modifier.wrapContentWidth()
        ) {
            Text("새로운 표지 만들기", style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Preview
@Composable
fun TitleScreenPreview() {
    CoveristTheme() {
    }
}