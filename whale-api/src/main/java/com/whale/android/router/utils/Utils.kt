package com.whale.android.router.utils

import android.content.Context
import android.widget.Toast
import com.whale.android.router.WhaleRouter
import com.whale.android.router.api.BuildConfig

class Utils {

    companion object {
        fun showDebugToast(context: Context, message:String) {
            if (WhaleRouter.debugMode) {
                Toast.makeText(context,message,Toast.LENGTH_LONG).show()
            }
        }
    }

}