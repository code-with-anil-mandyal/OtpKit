package com.codewithanil.otpkit.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign


@Composable
fun OtpInput(
    value: String,
    onValueChange: (String) -> Unit,
    onComplete: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = 6,
    style: OtpStyle = OtpStyle(),
    isError: Boolean = false
) {

    val focusRequester = remember { FocusRequester() }

    Box(modifier = modifier) {


        BasicTextField(
            value = value,
            onValueChange = { newValue ->

                val clean = newValue.filter { it.isDigit() }.take(length)

                onValueChange(clean)

                if (clean.length == length) {
                    onComplete(clean)
                }
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .alpha(0f), // hide
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            cursorBrush = SolidColor(style.textColor)
        )


        Row(
            horizontalArrangement = Arrangement.spacedBy(style.spacing)
        ) {

            repeat(length) { index ->

                val char = value.getOrNull(index)?.toString() ?: ""

                val isFocused = value.length == index

                val borderColor = when {
                    isError -> style.errorBorderColor
                    isFocused -> style.focusedBorderColor
                    char.isNotEmpty() -> style.filledBorderColor
                    else -> style.emptyBorderColor
                }

                val shape = when (style.cellType) {
                    OtpCellType.CIRCLE -> CircleShape
                    else -> style.shape
                }

                Box(
                    modifier = Modifier
                        .size(style.cellSize)
                        .clickable {
                            focusRequester.requestFocus()
                        }
                        .then(
                            when (style.cellType) {
                                OtpCellType.LINE -> Modifier.drawBehind {
                                    val stroke = style.borderWidth.toPx()
                                    val y = size.height - stroke / 2
                                    drawLine(
                                        color = borderColor,
                                        start = Offset(0f, y),
                                        end = Offset(size.width, y),
                                        strokeWidth = stroke
                                    )
                                }

                                else -> Modifier.border(
                                    width = style.borderWidth,
                                    color = borderColor,
                                    shape = shape
                                )
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = char,
                        fontSize = style.textSize,
                        color = style.textColor,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}