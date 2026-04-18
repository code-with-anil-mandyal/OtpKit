package com.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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


    }

}