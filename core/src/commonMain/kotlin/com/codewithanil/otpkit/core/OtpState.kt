package com.core

data class OtpState(
    val otp: List<String>,
    val isComplete: Boolean,
    val currentIndex: Int
)