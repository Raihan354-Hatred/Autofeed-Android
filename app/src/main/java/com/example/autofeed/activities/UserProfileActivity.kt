package com.example.autofeed.activities

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.autofeed.R
import com.example.autofeed.models.User
import com.example.autofeed.utils.Constants
import kotlinx.android.synthetic.main.activity_user_profile.*
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.autofeed.firestore.FirestoreClass
import com.example.autofeed.utils.GlideLoader
import java.io.IOException

class UserProfileActivity : BaseActivity(), View.OnClickListener {
    private lateinit var mUserDetails: User
    private var mSelectedImageFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        et_firstName_profile.isEnabled = false
        et_firstName_profile.setText(mUserDetails.firstName)

        et_lastName_profile.isEnabled = false
        et_lastName_profile.setText(mUserDetails.lastName)

        et_email_profile.isEnabled = false
        et_email_profile.setText(mUserDetails.email)

        iv_imageProfile.setOnClickListener(this@UserProfileActivity)
        btn_saveProfile.setOnClickListener(this@UserProfileActivity)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.iv_imageProfile -> {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.showImageChooser(this)
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }

                R.id.btn_saveProfile -> {
                    showProgressDialog(resources.getString(R.string.pleaseWait))

                    FirestoreClass().uploadImageToCloudStorage(this, mSelectedImageFileUri)
                    /*
                    if(validateUserProfileDetails()){
                        val userHashMap = HashMap<String, Any>()

                        val noHP = et_noHP_profile.text.toString().trim { it <= ' ' }

                        val gender = if (rb_male.isChecked){
                            Constants.MALE
                        } else {
                            Constants.FEMALE
                        }

                        if (noHP.isNotEmpty()){
                            userHashMap[Constants.noHP] = noHP.toLong()
                        }

                        userHashMap[Constants.GENDER] = gender
                        showProgressDialog(resources.getString(R.string.pleaseWait))
                        FirestoreClass().updateUserProfileData(this, userHashMap)

                        //showErrorSnackBar("Success!", false)
                    }
                     */
                }
            }
        }
    }

    fun userProfileUpdateSuccess(){
        hideProgressDialog()

        Toast.makeText(
            this,
            resources.getString(R.string.msg_profileUpdateSuccess),
            Toast.LENGTH_SHORT
        ).show()

        startActivity(Intent(this@UserProfileActivity, MainActivity::class.java))
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Constants.showImageChooser(this)
        } else {
            Toast.makeText(
                this,
                resources.getString(R.string.read_StoragePermissionDenied),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (resultCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        mSelectedImageFileUri = data.data!!
//                        iv_imageProfile.setImageURI(selectedImageFileUri)
                        GlideLoader(this).loadUserPicture(mSelectedImageFileUri!!,iv_imageProfile)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this,
                            resources.getString(R.string.image_selectionFailed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED){
            Log.e("Request dibatalkan","Batal")
        }
    }

    private fun validateUserProfileDetails(): Boolean{
        return when {
            TextUtils.isEmpty(et_noHP_profile.text.toString().trim(){ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msgNoHP), true)
                false
            } else -> {
                true
            }
        }
    }

    fun imageUploadSuccess(imageURL: String){
        hideProgressDialog()
        Toast.makeText(
            this,
            "Foto berhasil diunggah. $imageURL",
            Toast.LENGTH_SHORT
        ).show()
    }

}