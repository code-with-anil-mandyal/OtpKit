package com.core

data class OtpState(
    val otp: List<String>,
    val currentIndex: Int,
    val isComplete: Boolean
)