package com.codewithanil.otpkit.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.codewithanil.otpkit.core.OtpManager
import com.codewithanil.otpkit.core.OtpState


class OtpView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var length = 6
    private var cellType = 0

    private val otpManager = OtpManager(length)
    private val editTexts = mutableListOf<OtpEditText>()
    private val lineViews = mutableListOf<View?>()

    private var borderColor = Color.GRAY
    private var focusedBorderColor = Color.BLUE
    private var filledBorderColor = Color.BLACK
    private var errorBorderColor = Color.RED

    private var textColor = Color.BLACK
    private var textSizePx = spToPx(18f)

    private var cornerRadius = dp(8).toFloat()
    private var borderWidth = dp(2)

    private var isErrorState = false
    private var onComplete: ((String) -> Unit)? = null

    private var isInternalUpdate = false

    init {
        orientation = HORIZONTAL

        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.OtpView)

            length = ta.getInt(R.styleable.OtpView_otpLength, 6)
            cellType = ta.getInt(R.styleable.OtpView_cellType, 0)

            borderColor = ta.getColor(R.styleable.OtpView_borderColor, borderColor)
            focusedBorderColor = ta.getColor(R.styleable.OtpView_focusedBorderColor, focusedBorderColor)
            filledBorderColor = ta.getColor(R.styleable.OtpView_filledBorderColor, filledBorderColor)
            errorBorderColor = ta.getColor(R.styleable.OtpView_errorBorderColor, errorBorderColor)

            textColor = ta.getColor(R.styleable.OtpView_textColor, textColor)
            textSizePx = ta.getDimension(R.styleable.OtpView_textSize, textSizePx)

            cornerRadius = ta.getDimension(R.styleable.OtpView_cornerRadius, cornerRadius)
            borderWidth = ta.getDimensionPixelSize(R.styleable.OtpView_borderWidth, borderWidth)

            ta.recycle()
        }

        setupViews()
    }

    private fun setupViews() {
        repeat(length) { index ->
            val view = if (cellType == 2) createLineCell(index) else createBoxCell(index)
            addView(view)
        }
    }

    // ---------------- BOX / CIRCLE ----------------

    private fun createBoxCell(index: Int): OtpEditText {

        val et = OtpEditText(context).apply {

            layoutParams = LayoutParams(dp(48), dp(48)).apply {
                if (index != length - 1) {
                    marginEnd = dp(8)
                }
            }

            gravity = Gravity.CENTER
            inputType = InputType.TYPE_CLASS_NUMBER
            filters = arrayOf(InputFilter.LengthFilter(1))

            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePx)
            setTextColor(textColor)

            setBackgroundColor(Color.TRANSPARENT)
            backgroundTintList = null

            highlightColor = Color.TRANSPARENT

            background = createBackground(false, false, false)


            setPadding(dp(8), dp(8), dp(8), dp(8))
        }

        setupListeners(et, index, null)

        editTexts.add(et)
        lineViews.add(null)

        return et
    }

    // ---------------- LINE ----------------

    private fun createLineCell(index: Int): View {
        val container = LinearLayout(context).apply {
            orientation = VERTICAL
            layoutParams = LayoutParams(dp(48), dp(48)).apply {
                if (index != length - 1) {
                    marginEnd = dp(8)
                }
            }
        }

        val et = OtpEditText(context).apply {
            gravity = Gravity.CENTER
            inputType = InputType.TYPE_CLASS_NUMBER
            filters = arrayOf(InputFilter.LengthFilter(1))
            background = null
            backgroundTintList = null

            highlightColor = Color.TRANSPARENT

            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePx)
            setTextColor(textColor)
        }

        val line = View(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, borderWidth)
            setBackgroundColor(borderColor)
        }

        container.addView(et, LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f))
        container.addView(line)

        setupListeners(et, index, line)

        editTexts.add(et)
        lineViews.add(line)

        return container
    }

    // ---------------- LISTENERS ----------------

    private fun setupListeners(et: OtpEditText, index: Int, line: View?) {

        et.onPaste = { handlePaste(it) }


        et.setOnFocusChangeListener { _, hasFocus ->
            updateUI(et, index, hasFocus)
        }


        et.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

                if (isInternalUpdate) return

                setError(false)

                val value = s.toString()


                if (editTexts.all { it.text?.isEmpty() == true }) {
                    otpManager.clear()
                    return
                }

                if (value.length == 1) {

                    val state = otpManager.setAt(index, value.first())

                    moveNext(index)
                    checkComplete(state)
                }

                updateUI(et, index, et.isFocused)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        et.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {

                val text = et.text
                if (!text.isNullOrEmpty()) {
                    text.clear()
                    otpManager.clearAt(index)
                } else if (index > 0) {
                    val prev = editTexts[index - 1]
                    prev.requestFocus()
                    prev.text?.clear()
                    otpManager.clearAt(index - 1)
                }

                true
            } else {
                false
            }
        }
    }

    // ---------------- UI ----------------

    private fun updateUI(et: EditText, index: Int, isFocused: Boolean) {
        val isFilled = et.text.isNotEmpty()

        val drawable = createBackground(isFocused, isFilled, isErrorState)

        if (cellType == 2) {
            val color = when {
                isErrorState -> errorBorderColor
                isFocused -> focusedBorderColor
                isFilled -> filledBorderColor
                else -> borderColor
            }
            lineViews[index]?.setBackgroundColor(color)
        } else {
            et.background = drawable
        }
    }

    private fun createBackground(
        isFocused: Boolean,
        isFilled: Boolean,
        isError: Boolean
    ): GradientDrawable {

        val color = when {
            isError -> errorBorderColor
            isFocused -> focusedBorderColor
            isFilled -> filledBorderColor
            else -> borderColor
        }

        return GradientDrawable().apply {
            shape = if (cellType == 1) GradientDrawable.OVAL else GradientDrawable.RECTANGLE
            setStroke(borderWidth, color)
            cornerRadius = this@OtpView.cornerRadius

            setColor(Color.TRANSPARENT)
        }
    }

    // ---------------- PASTE ----------------

    private fun handlePaste(value: String) {

        val clean = value.filter { it.isDigit() }

        isInternalUpdate = true

        otpManager.clear()
        editTexts.forEach { it.text?.clear() }

        val state = otpManager.setOtp(clean)

        state.otp.forEachIndexed { i, c ->
            editTexts[i].setText(c)
            updateUI(editTexts[i], i, false)
        }

        isInternalUpdate = false

        val last = state.otp.indexOfLast { it.isNotEmpty() }
        if (last >= 0) editTexts[last].requestFocus()

        checkComplete(state)
    }

    // ---------------- NAV ----------------

    private fun moveNext(index: Int) {
        if (index < editTexts.size - 1) {
            editTexts[index + 1].requestFocus()
        }
    }

    // ---------------- PUBLIC ----------------

    fun getOtp(): String = otpManager.getOtp()

    fun setOtp(value: String) = handlePaste(value)

    fun clearOtp() {
        otpManager.clear()
        editTexts.forEachIndexed { i, et ->
            et.text?.clear()
            updateUI(et, i, false)
        }
    }

    fun setError(isError: Boolean) {
        isErrorState = isError
        editTexts.forEachIndexed { i, et ->
            updateUI(et, i, et.isFocused)
        }
    }

    fun setOnOtpCompleteListener(listener: (String) -> Unit) {
        onComplete = listener
    }

//    private fun checkComplete(state: OtpState) {
//        if (state.isComplete) {
//            onComplete?.invoke(state.otp.joinToString(""))
//        }
//    }

    private fun checkComplete(state: OtpState) {

        val otp = state.otp
            .filter { it.isNotEmpty() }
            .joinToString("")

        if (otp.length == length) {
            onComplete?.invoke(otp)
        }
    }

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private fun spToPx(v: Float) = v * resources.displayMetrics.scaledDensity
}