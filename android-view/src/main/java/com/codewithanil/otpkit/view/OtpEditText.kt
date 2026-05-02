package com.codewithanil.otpkit.view

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.widget.EditText

@SuppressLint("AppCompatCustomView")
class OtpEditText(context: Context) : EditText(context) {

    var onPaste: ((String) -> Unit)? = null

    override fun onTextContextMenuItem(id: Int): Boolean {
        if (id == android.R.id.paste) {
            val clipboard =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            val clip = clipboard.primaryClip

            if (clip != null && clip.itemCount > 0) {
                val pasteData = clip.getItemAt(0).text.toString()
                onPaste?.invoke(pasteData)
                return true
            }
        }
        return super.onTextContextMenuItem(id)
    }
}