package com.example.todak_ai.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LinedDiaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    minHeight : Int = 200,
    lineHeight : Int = 40,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = lineHeight.sp, // 줄 간격
            fontSize = 20.sp
        ),
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = minHeight.dp)
            .padding(horizontal = 15.dp),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .drawBehind {
                        val lineHeightPx = lineHeight.sp.toPx() // 줄 간격
                        val totalLines = (size.height / lineHeightPx).toInt()
                        repeat(totalLines) { i ->
                            val y = (i + 1) * lineHeightPx
                            drawLine(
                                color = Color.LightGray,
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                    }
            ) {
                innerTextField()
            }
        }
    )
}
