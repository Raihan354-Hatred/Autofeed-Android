package com.example.autofeed.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.example.autofeed.R
import com.example.autofeed.firestore.FirestoreClass
import com.example.autofeed.models.User
import com.example.autofeed.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        tv_forgotPasswordLogin.setOnClickListener(this)

        btn_login.setOnClickListener(this)

        tv_register.setOnClickListener(this)

    }

    fun userLoggedInSuccess(user: User){
        hideProgressDialog()

        Log.i("First Name: ",user.firstName)
        Log.i("Last Name: ",user.lastName)
        Log.i("Email: ",user.email)

        if(user.profileComplete == 0 ){
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        } else {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
        finish()
    }

    override fun onClick(view: View?){
        if (view != null){
            when (view.id){

                R.id.tv_forgotPasswordLogin -> {
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.btn_login -> {
                    loginRegisteredUser()
                }

                R.id.tv_register -> {
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun validateLogin():Boolean{
        return when {
            TextUtils.isEmpty(et_emailLogin.text.toString().trim{it <=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msgEnterEmail),true)
                false
            }
            TextUtils.isEmpty(et_passwordLogin.text.toString().trim{it <=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msgEnterPassword),true)
                false
            }
            else -> {
                showErrorSnackBar("Anda berhasil Login",false)
                true
            }
        }
    }

    private fun loginRegisteredUser(){
        if(validateLogin()) {
            showProgressDialog(resources.getString(R.string.pleaseWait))

            val email = et_emailLogin.text.toString().trim{ it <= ' '}
            val password = et_passwordLogin.text.toString().trim{ it <= ' '}

            //LOGIN FIREBASE
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        FirestoreClass().getUserDetails(this@LoginActivity)
                        } else {
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }
                }
        }
    }
}