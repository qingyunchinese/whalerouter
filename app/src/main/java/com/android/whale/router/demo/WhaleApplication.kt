package com.android.whale.router.demo

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.android.whale.router.api.interceptor.RouterInterceptor
import com.whale.android.router.WhaleRouter

class WhaleApplication : Application(), RouterInterceptor {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        /**
         * init WhaleRouter
         */
        WhaleRouter.init(this, "whale")
        WhaleRouter.addRouteInterceptor(this)
    }

}