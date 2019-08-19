package com.android.whale.router.demo

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class WhaleProduct(val id: Int? = null,
                        val name: String? = null,
                        val access: Boolean? = false) : Parcelable