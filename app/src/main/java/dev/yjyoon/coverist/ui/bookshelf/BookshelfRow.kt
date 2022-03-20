package dev.yjyoon.coverist.ui.bookshelf

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun BookShelfRow(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            "내 책장",
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(18.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            contentPadding = PaddingValues(horizontal = 18.dp)
        ) {
            items(5) { index ->
                Card(
                    shape = RoundedCornerShape(6.dp),
                    elevation = 5.dp
                ) {
                    AsyncImage(
                        model = "https://image.yes24.com/goods/89990069/XL",
                        contentDescription = null,
                        modifier = Modifier
                            .width(144.dp)
                            .clickable { navController.navigate("bookshelf-detail") },
                    )
                }
            }
        }
    }
}