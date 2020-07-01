package com.whale.android.router.meta

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import androidx.core.app.ActivityOptionsCompat
import java.io.Serializable
import java.util.*


class RouterRequest(val routerPath: String) {

    val extra: Bundle by lazy {
        Bundle()
    }

    private var requestCode = -1
    private var flags = -1
    private var enterAnim = -1
    private var exitAnim = -1
    private var optionsCompat: ActivityOptionsCompat? = null
    private var action: String? = null

    fun withAction(action: String) = apply {
        this.action = action
    }

    fun withRequestCode(requestCode: Int) = apply {
        this.requestCode = requestCode
    }

    fun addFlag(flags: Int) = apply {
        this.flags = this.flags or flags
    }

    fun withTransition(enterAnim: Int, exitAnim: Int) = apply {
        this.enterAnim = enterAnim
        this.exitAnim = exitAnim
    }

    fun withOptionsCompat(activityOptionsCompat: ActivityOptionsCompat) = apply {
        this.optionsCompat = activityOptionsCompat
    }

    fun getEnterAnim(): Int = enterAnim

    fun getFlags(): Int = flags

    fun getExitAnim(): Int = exitAnim

    fun getAction(): String? = action

    fun getActivityOptionsCompat(): ActivityOptionsCompat? = optionsCompat

    fun getRequestCode(): Int = requestCode

    fun withBundle(key: String, value: Bundle) = apply {
        extra.putBundle(key, value)
    }

    fun withCharSequenceArray(key: String, value: Array<CharSequence>) = apply {
        extra.putCharSequenceArray(key, value)
    }

    fun withFloatArray(key: String, value: FloatArray) = apply {
        extra.putFloatArray(key, value)
    }

    fun withCharArray(key: String, value: CharArray) = apply {
        extra.putCharArray(key, value)
    }


    fun withShortArray(key: String, value: ShortArray) = apply {
        extra.putShortArray(key, value)
    }

    fun withByteArray(key: String, value: ByteArray) = apply {
        extra.putByteArray(key, value)
    }

    fun withCharSequenceArrayList(key: String, value: ArrayList<CharSequence>) = apply {
        extra.putCharSequenceArrayList(key, value)
    }

    fun withStringArrayList(key: String, value: ArrayList<String>) = apply {
        extra.putStringArrayList(key, value)
    }

    fun withIntegerArrayList(key: String, value: ArrayList<Int>) = apply {
        extra.putIntegerArrayList(key, value)
    }

    fun withSparseParcelableArray(key: String, value: SparseArray<out Parcelable>) = apply {
        extra.putSparseParcelableArray(key, value)
    }

    fun withParcelableArrayList(key: String, value: ArrayList<out Parcelable>) = apply {
        extra.putParcelableArrayList(key, value)
    }

    fun withParcelableArray(key: String, value: Array<Parcelable>) = apply {
        extra.putParcelableArray(key, value)
    }

    fun withCharSequence(key: String, value: CharSequence) = apply {
        extra.putCharSequence(key, value)
    }

    fun withChar(key: String, value: Char) = apply {
        extra.putChar(key, value)
    }

    fun withByte(key: String, value: Byte) = apply {
        extra.putByte(key, value)
    }

    fun withDouble(key: String, value: Double) = apply {
        extra.putDouble(key, value)
    }

    fun withLong(key: String, value: Long) = apply {
        extra.putLong(key, value)
    }

    fun withInt(key: String, value: Int) = apply {
        extra.putInt(key, value)
    }

    fun withString(key: String, value: String) = apply {
        extra.putString(key, value)
    }

    fun withBoolean(key: String, value: Boolean) = apply {
        extra.putBoolean(key, value)
    }

    fun withShort(key: String, value: Short) = apply {
        extra.putShort(key, value)
    }

    fun withSerializable(key: String, value: Serializable) = apply {
        extra.putSerializable(key, value)
    }

    fun withParcelable(key: String, value: Parcelable) = apply {
        extra.putParcelable(key, value)
    }
}