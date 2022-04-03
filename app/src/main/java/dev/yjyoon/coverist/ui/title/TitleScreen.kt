package dev.yjyoon.coverist.ui.title

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Web
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.yjyoon.coverist.BuildConfig
import dev.yjyoon.coverist.ui.bookshelf.BookShelfRow
import dev.yjyoon.coverist.ui.bookshelf.BookshelfViewModel
import dev.yjyoon.coverist.ui.theme.TitleDrawerShape
import kotlinx.coroutines.launch

@Composable
fun TitleScreen(
    navController: NavController,
    bookShelfViewModel: BookshelfViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { TitleDrawerContent() },
        drawerShape = TitleDrawerShape,
        bottomBar = {
            StartButton(
                onClick = {
                    navController.navigate("cover-generation")
                },
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxHeight()
                .padding(innerPadding)
        ) {
            TitleGraphic(
                configuration = configuration,
                onMenuClick = {
                    scope.launch {
                        scaffoldState.drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                })
            BookShelfRow(navController = navController, viewModel = bookShelfViewModel)
        }
    }
}

@Composable
fun TitleGraphic(
    modifier: Modifier = Modifier,
    configuration: Configuration,
    onMenuClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(configuration.screenWidthDp.dp)
            .background(color = MaterialTheme.colors.primary)
    ) {
        /*
        AsyncImage(
            "https://mblogthumb-phinf.pstatic.net/MjAyMDA3MTRfNzAg/MDAxNTk0Njk2ODE2MTIy.pv4Ij7GFJwXLBKhwL2Jjcj59WdDc5hfdmzdVjUycDHkg.x51bDgp1jKX3SuQsWoHdWIu9OcaBOrpy5gtWmr9niWAg.PNG.zencstory/SE-b3c3d58a-e05d-4285-a9bb-8a9c67e07643.png?type=w800",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .blur(4.dp)

        )*/
        Text(
            "내 작품의 새로운 얼굴,\n이젠 쉽고 간편하게",
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = Offset(4f, 4f),
                    blurRadius = 8f
                )
            ),
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(18.dp)
        )
        IconButton(
            onClick = onMenuClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(6.dp)
        ) {
            Icon(
                Icons.Rounded.Menu,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun TitleDrawerContent() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.primary)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
        }
        Divider()
        LazyColumn {
            item {
                Spacer(Modifier.height(16.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(vertical = 20.dp, horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.Web, contentDescription = null, tint = Color.Gray)
                    Spacer(Modifier.width(16.dp))
                    Text("웹페이지 바로가기")
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(vertical = 20.dp, horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.Info, contentDescription = null, tint = Color.Gray)
                    Spacer(Modifier.width(16.dp))
                    Text("오픈소스 라이센스")
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "버전 ${BuildConfig.VERSION_NAME}",
                modifier = Modifier.align(Alignment.BottomStart),
                style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun StartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 14.dp, horizontal = 20.dp),
        modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth()
    ) {
        Text(
            "새로운 책 표지 만들기",
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
        )
    }

}

@Preview
@Composable
fun TitleScreenPreview() {
}