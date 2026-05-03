package com.codewithanil.otpkit.core


class OtpManager(private val length: Int) {

    private val otpList = MutableList(length) { "" }

    fun setAt(index: Int, char: Char): OtpState {
        if (index in otpList.indices) {
            otpList[index] = char.toString()
        }
        return getState()
    }

    fun clearAt(index: Int): OtpState {
        if (index in otpList.indices) {
            otpList[index] = ""
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

        return getState()
    }

    fun clear(): OtpState {
        for (i in otpList.indices) {
            otpList[i] = ""
        }
        return getState()
    }

    fun getOtp(): String = otpList.joinToString("")

    fun isComplete(): Boolean = otpList.all { it.isNotEmpty() }

    fun getState(): OtpState {
        val currentIndex = otpList.indexOfFirst { it.isEmpty() }
            .let { if (it == -1) length - 1 else it }

        return OtpState(
            otp = otpList.toList(),
            isComplete = isComplete(),
            currentIndex = currentIndex
        )
    }

    fun input(char: Char): OtpState {
        val index = otpList.indexOfFirst { it.isEmpty() }
        return if (index != -1) setAt(index, char) else getState()
    }

    fun delete(): OtpState {
        val index = otpList.indexOfLast { it.isNotEmpty() }
        return if (index != -1) clearAt(index) else getState()
    }
}