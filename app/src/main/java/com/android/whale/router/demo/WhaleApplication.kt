package com.android.whale.router.demo

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class WhaleApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        WhaleRouterManager.init(this)
    }
}