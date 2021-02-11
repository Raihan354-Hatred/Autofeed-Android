package com.example.autofeed.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.autofeed.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_register.*

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_forgotPassword)

        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back24)
        }
        toolbar_forgotPassword.setNavigationOnClickListener{onBackPressed()}

        btn_forgotPassword.setOnClickListener{
            val email: String = et_emailForgotPassword.text.toString().trim { it <= ' ' }
            if (email.isEmpty()){
                showErrorSnackBar(resources.getString(R.string.err_msgEnterEmail),true)
            } else {
                showProgressDialog(resources.getString(R.string.pleaseWait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->
                        hideProgressDialog()
                        if (task.isSuccessful){
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                resources.getString(R.string.emailSentSuccess),
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }else{
                            showErrorSnackBar(task.exception!!.message.toString(),true)
                        }
                    }
            }
    }
}}