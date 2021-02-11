package com.example.autofeed.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.example.autofeed.R
import com.example.autofeed.firestore.FirestoreClass
import com.example.autofeed.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        tv_login.setOnClickListener{
            onBackPressed()
        }

        btn_register.setOnClickListener{
            registerUser()
        }
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbarRegister)

        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back24)
        }
        toolbarRegister.setNavigationOnClickListener{onBackPressed()}
    }

    private fun validateRegister():Boolean{
        return when {
            TextUtils.isEmpty(et_firstName.text.toString().trim{it <=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msgEnterFirstName),true)
                false
            }

            TextUtils.isEmpty(et_emailRegister.text.toString().trim{it <=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msgEnterEmail),true)
                false
            }

            TextUtils.isEmpty(et_passwordRegister.text.toString().trim{it <=' '}) || et_passwordRegister.length() <= 7 -> {
                showErrorSnackBar(resources.getString(R.string.err_msgEnterPassword),true)
                false
            }

            TextUtils.isEmpty(et_confirmPasswordRegister.text.toString().trim{it <=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msgEnterConfirmPassword),true)
                false
            }

            TextUtils.isEmpty(et_confirmPasswordRegister.text.toString().trim{it <=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msgMissMatchPassword),true)
                false
            }

            et_passwordRegister.text.toString().trim{it <= ' '} != et_confirmPasswordRegister.text.toString().trim { it <= ' '} -> {
                showErrorSnackBar(resources.getString(R.string.err_msgMissMatchPassword),true)
                false
            }

            !cb_termsCondition.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msgTermsCondition),true)
                false
            }

            else -> {
                //showErrorSnackBar(resources.getString(R.string.success_register), false)
                true
            }
        }
    }

    private fun registerUser(){

        if (validateRegister()){

            showProgressDialog(resources.getString(R.string.pleaseWait))

            val email: String = et_emailRegister.text.toString().trim{ it <= ' '}
            val password: String = et_passwordRegister.text.toString().trim{it <=' '}

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(
                    OnCompleteListener <AuthResult> { task ->
                        if(task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            val user = User(
                                firebaseUser.uid,
                                et_firstName.text.toString().trim { it <= ' '},
                                et_lastName.text.toString().trim { it <= ' '},
                                et_emailRegister.text.toString().trim { it <= ' '}
                            )
                            FirestoreClass().registerUserFirestore(this@RegisterActivity,user)
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            //FirebaseAuth.getInstance().signOut()
                            //finish()

                        } else {
                            hideProgressDialog()
                            showErrorSnackBar(task.exception!!.message.toString(),true)
                        }
                    })
        }
    }

    fun userRegisterSuccess(){
        hideProgressDialog()
        Toast.makeText(
            this@RegisterActivity,
            resources.getString(R.string.success_register),
            Toast.LENGTH_SHORT
        ).show()
    }

}