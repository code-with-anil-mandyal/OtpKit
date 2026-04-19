package com.codewithanil.otpkit.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import com.core.OtpState


@Composable
fun OtpInput(
    state: OtpState,
    onInput: (Char) -> Unit,
    onDelete: () -> Unit,
    onPaste: (String) -> Unit,
    onComplete: (String) -> Unit,
    modifier: Modifier = Modifier,
    style: OtpStyle = OtpStyle(),
    config: OtpConfig = OtpConfig(),
    isError: Boolean = false
) {

    // Complete callback
    LaunchedEffect(state.isComplete) {
        if (state.isComplete) {
            onComplete(state.otp.joinToString(""))
        }
    }

    var textFieldValue by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Box(modifier = modifier) {

        BasicTextField(
            value = textFieldValue,
            onValueChange = { newValue ->

                if (!config.isEnabled) return@BasicTextField

                when {
                    newValue.length - textFieldValue.length > 1 -> {
                        onPaste(newValue)
                    }

                    newValue.length > textFieldValue.length -> {
                        onInput(newValue.last())
                    }

                    newValue.length < textFieldValue.length -> {
                        onDelete()
                    }
                }

                textFieldValue = state.otp.joinToString("")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .focusRequester(focusRequester)
        )

        Row(
            modifier = Modifier.clickable {
                if (config.isEnabled) focusRequester.requestFocus()
            },
            horizontalArrangement = Arrangement.spacedBy(style.spacing)
        ) {

            state.otp.forEachIndexed { index, value ->

                OtpCell(
                    value = value,
                    isFocused = index == state.currentIndex,
                    isError = isError,
                    style = style,
                    isMasked = config.isMasked
                )
            }
        }
    }
}