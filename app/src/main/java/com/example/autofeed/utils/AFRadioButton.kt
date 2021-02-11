package com.example.autofeed.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class AFRadioButton(context: Context, attributeSet: AttributeSet):AppCompatRadioButton(context,attributeSet) {
    init {
        applyFont()
    }

    private fun applyFont(){
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets, "Poppins-Regular.ttf")
        setTypeface(typeface)
    }

}