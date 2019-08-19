package com.whale.android.router.log

import android.text.TextUtils
import android.util.Log
import com.whale.android.router.Constants

class AndroidLogger(var logEnable:Boolean) : ILogger {

    override fun verbose(tag: String, message: String) {
        if (logEnable) {
            Log.v(if (TextUtils.isEmpty(tag)) Constants.GLOBALTAG else tag, message)
        }
    }

    override fun debug(tag: String, message: String) {
        if (logEnable) {
            Log.d(if (TextUtils.isEmpty(tag)) Constants.GLOBALTAG else tag, message)
        }
    }

    override fun info(tag: String, message: String) {
        if (logEnable) {
            Log.i(if (TextUtils.isEmpty(tag)) Constants.GLOBALTAG else tag, message)
        }
    }

    override fun error(tag: String, message: String) {
        if (logEnable) {
            Log.e(if (TextUtils.isEmpty(tag)) Constants.GLOBALTAG else tag, message)
        }
    }

    override fun warning(tag: String, message: String) {
        if (logEnable) {
            Log.w(if (TextUtils.isEmpty(tag)) Constants.GLOBALTAG else tag, message)
        }
    }

}