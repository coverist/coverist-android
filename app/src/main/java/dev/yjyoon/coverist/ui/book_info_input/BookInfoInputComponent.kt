package dev.yjyoon.coverist.ui.book_info_input

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.yjyoon.coverist.R
import dev.yjyoon.coverist.ui.theme.CoveristTheme
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

@Composable
fun UploadPublisherImage(
    imageUri: Uri?,
    onUpload: (Uri) -> Unit,
    onDelete: () -> Unit,
    setEmpty: (Boolean) -> Unit
) {
    val context = LocalContext.current

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = onUpload
    )

    var isEmpty by remember { mutableStateOf(false) }

    // API level 28 이하는 deprecated 된 MediaStore.Images.Media.getBitmap 사용
    // 그 이상부터 ImageDecoder.createSource 사용
    imageUri?.let {
        bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }
    }

    if (bitmap == null) {

        Column {
            Surface(
                shape = MaterialTheme.shapes.medium
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isEmpty = !isEmpty
                            setEmpty(isEmpty)
                        }
                        .padding(8.dp)

                ) {
                    Text(
                        "출판사 이미지가 따로 없어요",
                        color = if (isEmpty) MaterialTheme.colors.primary
                        else MaterialTheme.colors.onSurface.copy(alpha = 0.87f)
                    )
                    Checkbox(
                        checked = isEmpty,
                        onCheckedChange = {
                            isEmpty = it
                            setEmpty(it)
                        },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
                    )
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            AnimatedVisibility(
                visible = !isEmpty,
                modifier = Modifier.weight(1f)
            ) {
                OutlinedButton(
                    onClick = { launcher.launch("image/*") },
                    enabled = !isEmpty,
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 18.dp)
                ) {
                    val imageAssetId = R.drawable.ic_undraw_upload_re_pasx

                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = imageAssetId),
                            contentDescription = null,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Rounded.AddPhotoAlternate, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("이미지 업로드")
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                }
            }
        }

    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(Icons.Rounded.SwapHoriz, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("다시 업로드")
                }
                Spacer(modifier = Modifier.width(12.dp))
                TextButton(
                    onClick = {
                        onDelete()
                        bitmap = null
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(Icons.Rounded.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("삭제")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TagsRowPreview() {
    CoveristTheme() {
        UploadPublisherImage(onUpload = {}, onDelete = {}, imageUri = null, setEmpty = {})
    }
}