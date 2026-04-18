package com.core

class OtpManager(
    private val length: Int
) {
    private val otpList = MutableList(length) { "" }
    private var currentIndex = 0

    fun input(value: Char): OtpState {
        if (!value.isDigit()) return getState()

        if (currentIndex < length) {
            otpList[currentIndex] = value.toString()
            currentIndex++
        }

        return getState()
    }

    fun delete(): OtpState {
        if (currentIndex > 0) {
            currentIndex--
            otpList[currentIndex] = ""
        }

        return getState()
    }

    fun paste(value: String): OtpState {
        val clean = value.filter { it.isDigit() }

        for (char in clean) {
            if (currentIndex >= length) break
            otpList[currentIndex] = char.toString()
            currentIndex++
        }

        return getState()
    }

    fun setOtp(value: String): OtpState {
        clear()

        val clean = value.filter { it.isDigit() }

        for (i in clean.indices) {
            if (i < length) {
                otpList[i] = clean[i].toString()
            }
        }

        currentIndex = otpList.indexOfFirst { it.isEmpty() }
            .let { if (it == -1) length else it }

        return getState()
    }

    fun clear(): OtpState {
        for (i in otpList.indices) {
            otpList[i] = ""
        }
        currentIndex = 0
        return getState()
    }

    fun getState(): OtpState {
        return OtpState(
            otp = otpList.toList(),
            currentIndex = currentIndex,
            isComplete = otpList.none { it.isEmpty() }
        )
    }

    fun getOtp(): String {
        return otpList.joinToString("")
    }
}