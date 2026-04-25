package com.codewithanil.otpkit.sample

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codewithanil.otpkit.compose.OtpCellType
import com.codewithanil.otpkit.compose.OtpInput
import com.codewithanil.otpkit.compose.OtpStyle

@Composable
fun OtpComposeTestScreen() {

    var otp by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        OtpInput(
            value = otp,
            onValueChange = {
                otp = it
                isError = false
            },
            onComplete = {
                Log.d("OTP_TEST", "OTP Complete: $it")
                Toast.makeText(context, "OTP Complete: $it", Toast.LENGTH_SHORT).show()
            },
            length = 6,
            isError = isError,
            style = OtpStyle(
                cellType = OtpCellType.CIRCLE,
                emptyBorderColor = Color.Gray,
                focusedBorderColor = Color.Blue,
                filledBorderColor = Color.Black,
                errorBorderColor = Color.Red,
                textSize = 18.sp,
                textColor = Color.Black,
                borderWidth = 2.dp,
                shape = RoundedCornerShape(10.dp)
            )
        )

        Spacer(modifier = Modifier.height(30.dp))


        Button(
            onClick = {
                otp = "987654"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Set OTP (987654)")
        }

        Spacer(modifier = Modifier.height(10.dp))


        Button(
            onClick = {
                Log.d("OTP_TEST", "Current OTP: $otp")
                Toast.makeText(context, "OTP: $otp", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get OTP")
        }

        Spacer(modifier = Modifier.height(10.dp))


        Button(
            onClick = {
                otp = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear OTP")
        }

        Spacer(modifier = Modifier.height(10.dp))


        Button(
            onClick = {
                isError = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show Error")
        }
    }
}