package com.example.autofeed.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User (
    val id: String = "",
    val firstName: String ="",
    val lastName: String ="",
    val email: String ="",
    val noHP: Long= 0,
    val image: String="",
    val gender: String="",
    val profileComplete: Int=0
): Parcelable