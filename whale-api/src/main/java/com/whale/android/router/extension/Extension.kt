package com.whale.android.router.extension

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.whale.android.router.WhaleRouter
import com.whale.android.router.callback.NavigateCallback
import com.whale.android.router.meta.RouterRequest

fun RouterRequest.navigate(
    fragment: Fragment,
    callback: NavigateCallback? = null
) {
    WhaleRouter.navigate(fragment.context, this, callback)
}

fun RouterRequest.navigate(
    activity: Activity,
    callback: NavigateCallback? = null
) {
    WhaleRouter.navigate(activity, this, callback)
}

fun RouterRequest.navigate(
    context: Context?=null,
    callback: NavigateCallback? = null
) {
    WhaleRouter.navigate(context, this, callback)
}