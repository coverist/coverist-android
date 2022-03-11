package dev.yjyoon.coverist.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.yjyoon.coverist.ui.theme.CoveristTheme

@Composable
fun QuestionDialog(
    title: String,
    question: String,
    onYes: () -> Unit,
    onNo: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = modifier
                .wrapContentSize(),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = modifier.padding(vertical = 18.dp, horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = modifier.height(18.dp))
                Text(question)
                Spacer(modifier = modifier.height(18.dp))
                Row(
                    modifier = Modifier.width(180.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onYes,
                        modifier = modifier.weight(1f)
                    ) {
                        Text("예")
                    }
                    Spacer(modifier = modifier.width(8.dp))
                    OutlinedButton(
                        onClick = onNo,
                        modifier = modifier.weight(1f)
                    ) {
                        Text("아니오")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun QuestionDialogPreview() {
    CoveristTheme {
        QuestionDialog(
            title = "커버리스트",
            question = "도서 표지 생성을 그만둘까요?",
            onYes = {},
            onNo = {},
            onDismissRequest = {})
    }
}
