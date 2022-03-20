package dev.yjyoon.coverist.ui.bookshelf

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import coil.compose.AsyncImage
import dev.yjyoon.coverist.ui.common.SimpleFlowRow
import dev.yjyoon.coverist.ui.cover.generation.input.TagChip

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookShelfDetail(
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetShape = MaterialTheme.shapes.large.copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        ),
        sheetPeekHeight = (configuration.screenHeightDp / 1.95).dp,
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    "마지막 벚꽃이 질 때",
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text("윤여준 저", color = Color.DarkGray)
                Text("소설 | 연애", color = Color.Gray)
                Spacer(Modifier.height(2.dp))
                SimpleFlowRow(
                    verticalGap = 6.dp,
                    horizontalGap = 4.dp,
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    for (i in 1..3) {
                        TagChip(
                            tag = "안드로이드",
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
                Divider(Modifier.padding(vertical = 8.dp))
                Text(
                    "이 책의 다른 표지",
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(4.dp))
                Row(
                    modifier = Modifier.height(128.dp)
                ) {
                    Card(

                        shape = MaterialTheme.shapes.small.copy(CornerSize(8.dp)),
                        modifier = Modifier.clickable { }
                    ) {
                        AsyncImage(
                            model = "https://miricanvas.zendesk.com/hc/article_attachments/900002704543/_____________1_.png",
                            contentDescription = null
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Card(

                        shape = MaterialTheme.shapes.small.copy(CornerSize(8.dp)),
                        modifier = Modifier.clickable { }
                    ) {
                        AsyncImage(
                            model = "https://miricanvas.zendesk.com/hc/article_attachments/900002704543/_____________1_.png",
                            contentDescription = null
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Card(

                        shape = MaterialTheme.shapes.small.copy(CornerSize(8.dp)),
                        modifier = Modifier.clickable { }
                    ) {
                        AsyncImage(
                            model = "https://miricanvas.zendesk.com/hc/article_attachments/900002704543/_____________1_.png",
                            contentDescription = null
                        )
                    }
                }
                Divider(Modifier.padding(vertical = 8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {}
                ) {
                    Text(
                        "이 책으로 새로운 표지 만들기",
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = "https://image.yes24.com/goods/89990069/XL",
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
                    shape = MaterialTheme.shapes.small.copy(CornerSize(8.dp)),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    AsyncImage(
                        model = "https://image.yes24.com/goods/89990069/XL",
                        contentDescription = null,
                        modifier = Modifier.width((configuration.screenWidthDp / 2).dp)
                    )
                }
            }
        }
    }
}