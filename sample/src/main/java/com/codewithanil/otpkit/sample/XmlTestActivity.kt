package com.codewithanil.otpkit.sample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codewithanil.otpkit.R
import com.codewithanil.otpkit.view.OtpView

class XmlTestActivity : AppCompatActivity() {

    private lateinit var otpView: OtpView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xml_test)

        otpView = findViewById(R.id.otpView)

        val btnSetOtp = findViewById<Button>(R.id.btnSetOtp)
        val btnGetOtp = findViewById<Button>(R.id.btnGetOtp)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val btnError = findViewById<Button>(R.id.btnError)

        // 🔥 OTP Complete Listener
        otpView.setOnOtpCompleteListener { otp ->
            Log.d("OTP_TEST", "OTP Complete: $otp")
        }

        // 🔥 Test setOtp()
        btnSetOtp.setOnClickListener {
            otpView.setOtp("987654")
        }

        // 🔥 Test getOtp()
        btnGetOtp.setOnClickListener {
            val otp = otpView.getOtp()
            Log.d("OTP_TEST", "Current OTP: $otp")
            Toast.makeText(this, "OTP: $otp", Toast.LENGTH_SHORT).show()
        }

        // 🔥 Test clearOtp()
        btnClear.setOnClickListener {
            otpView.clearOtp()
        }

        // 🔥 Test error state
        btnError.setOnClickListener {
            otpView.setError(true)
        }
    }
}