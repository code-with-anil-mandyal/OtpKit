package com.sample

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codewithanil.otpkit.compose.OtpCellType
import com.codewithanil.otpkit.compose.OtpConfig
import com.codewithanil.otpkit.compose.OtpInput
import com.codewithanil.otpkit.compose.OtpStyle
import com.core.OtpManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val otpManager = OtpManager(6)

        Log.d("OTP", otpManager.input('1').toString())
        Log.d("OTP", otpManager.input('2').toString())
        Log.d("OTP", otpManager.input('3').toString())

        Log.d("OTP", otpManager.delete().toString())

        Log.d("OTP", otpManager.paste("4567").toString())

        Log.d("OTP", otpManager.getState().toString())

        val otpManager2 = OtpManager(6)

        val state = otpManager2.setOtp("987654")

        Log.d("OTP_TEST", "State: $state")
        Log.d("OTP_TEST", "OTP: ${otpManager2.getOtp()}")
        Log.d("OTP_TEST", "Is Complete: ${state.isComplete}")
        Log.d("OTP_TEST", "Current Index: ${state.currentIndex}")


        setContent {
            var isError by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val otpManager = remember { OtpManager(6) }
                var state by remember { mutableStateOf(otpManager.getState()) }
                var isError by remember { mutableStateOf(false) }

                OtpInput(
                    state = state,
                    onInput = {
                        isError = false
                        state = otpManager.input(it)
                    },
                    onDelete = {
                        isError = false
                        state = otpManager.delete()
                    },
                    onPaste = {
                        isError = false
                        state = otpManager.setOtp(it)
                    },
                    onComplete = { otp ->
                        if (otp != "123456") {
                            isError = true
                        }
                    },
                    isError = isError,

                    // 🔥 FULL CUSTOMIZATION
                    style = OtpStyle(
                        cellSize = 56.dp,
                        spacing = 12.dp,
                        textSize = 20.sp,
                        shape = RoundedCornerShape(8.dp), // 🔥 radius control
                        focusedBorderColor = Color.DarkGray,
                        filledBorderColor = Color.Black,
                        emptyBorderColor = Color.Gray,
                        errorBorderColor = Color.Red,
                        cellType = OtpCellType.BOX  
                    ),

                    config = OtpConfig(
                        length = 6,
                        isMasked = false
                    )
                )
            }
        }

    }

}