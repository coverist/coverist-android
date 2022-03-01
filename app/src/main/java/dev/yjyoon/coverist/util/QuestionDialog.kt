package dev.yjyoon.coverist.util

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.yjyoon.coverist.ui.theme.CoveristTheme

@Composable
fun QuestionDialog(
    question: String,
    onYes: () -> Unit,
    onNo: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .wrapContentSize(),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier.padding(vertical = 18.dp, horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(question)
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.width(240.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onYes,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("예")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(
                        onClick = onNo,
                        modifier = Modifier.weight(1f)
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
        QuestionDialog(question = "도서 표지 생성을 그만두시겠습니까?", onYes = {}, onNo = {}, onDismissRequest = {})
    }
}
