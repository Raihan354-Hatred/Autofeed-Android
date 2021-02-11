package com.example.autofeed.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager.LayoutParams.*
import com.example.autofeed.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            FLAG_FULLSCREEN,
            FLAG_FULLSCREEN
        )

        Handler().postDelayed(
            {
                startActivity(Intent(this@SplashActivity, KalkulatorActivity::class.java))
                finish()
            },
        1500
        )

        val typeface: Typeface = Typeface.createFromAsset(assets,"Poppins-Bold.ttf")
        tv_Autofeed.typeface = typeface
    }
}