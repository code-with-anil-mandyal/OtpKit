package com.codewithanil.otpkit.compose

data class OtpConfig(
    val length: Int = 6,
    val isEnabled: Boolean = true,
    val isMasked: Boolean = false
)