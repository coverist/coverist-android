package dev.yjyoon.coverist.util

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.yjyoon.coverist.ui.theme.CoveristTheme

@Composable
fun TextInputDialog(
    onDismissRequest: () -> Unit,
    textFieldLabel: @Composable (() -> Unit)?,
    textFieldLeadingIcon: @Composable (() -> Unit)?,
    submitButtonText: String,
    onSubmit: (String) -> Unit,
    isTextFieldError: (String) -> Boolean
) {
    // To auto-focus on TextField when dialog show
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier
                .width(200.dp)
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.onPrimary
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var text by remember { mutableStateOf("") }

                TextField(
                    value = text,
                    onValueChange = { text = it },
                    singleLine = true,
                    leadingIcon = textFieldLeadingIcon,
                    label = textFieldLabel,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.04f)
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if(!isTextFieldError(text)) {
                                onSubmit(text)
                                onDismissRequest()
                            }
                        }
                    ),
                    isError = isTextFieldError(text),
                    modifier = Modifier.focusRequester(focusRequester)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        onSubmit(text)
                        onDismissRequest()
                    },
                    shape = CircleShape,
                    enabled = !isTextFieldError(text)
                ) {
                    Text(submitButtonText)
                }
            }
        }
    }
}

@Preview
@Composable
fun TextInputDialogPreview() {
    CoveristTheme() {
    }
}