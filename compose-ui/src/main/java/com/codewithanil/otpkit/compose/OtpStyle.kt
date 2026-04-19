package com.codewithanil.otpkit.compose

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class OtpStyle(
    val cellSize: Dp = 48.dp,
    val spacing: Dp = 8.dp,

    val textSize: TextUnit = 18.sp,
    val textColor: Color = Color.Black,

    val shape: Shape = RoundedCornerShape(8.dp),

    val borderWidth: Dp = 2.dp,

    val focusedBorderColor: Color = Color.Blue,
    val filledBorderColor: Color = Color.Black,
    val emptyBorderColor: Color = Color.Gray,
    val errorBorderColor: Color = Color.Red,

    val cellType: OtpCellType = OtpCellType.BOX
)