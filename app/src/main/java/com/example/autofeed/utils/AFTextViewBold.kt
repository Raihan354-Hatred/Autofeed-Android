package com.example.autofeed.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class AFTextViewBold(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    init {
        applyFont()
    }

    private fun applyFont() {
        val typeface: Typeface=
            Typeface.createFromAsset(context.assets,"Poppins-Bold.ttf")
        setTypeface(typeface)
    }
}