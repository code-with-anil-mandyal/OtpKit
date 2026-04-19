package com.codewithanil.otpkit.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpCell(
    value: String,
    isFocused: Boolean,
    isError: Boolean,
    style: OtpStyle,
    isMasked: Boolean
) {

    val displayValue = if (isMasked && value.isNotEmpty()) "•" else value

    val borderColor = when {
        isError -> style.errorBorderColor
        isFocused -> style.focusedBorderColor
        value.isNotEmpty() -> style.filledBorderColor
        else -> style.emptyBorderColor
    }

    val baseModifier = Modifier.size(style.cellSize)

    when (style.cellType) {

        OtpCellType.BOX -> {
            Box(
                modifier = baseModifier.border(
                    width = style.borderWidth,
                    color = borderColor,
                    shape = style.shape
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(displayValue, fontSize = style.textSize, color = style.textColor)
            }
        }

        OtpCellType.CIRCLE -> {
            Box(
                modifier = baseModifier.border(
                    width = style.borderWidth,
                    color = borderColor,
                    shape = CircleShape
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(displayValue, fontSize = style.textSize, color = style.textColor)
            }
        }

        OtpCellType.LINE -> {
            Box(
                modifier = baseModifier,
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = displayValue,
                        fontSize = style.textSize,
                        color = style.textColor
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .height(style.borderWidth)
                            .fillMaxWidth()
                            .background(borderColor)
                    )
                }
            }
        }
    }
}