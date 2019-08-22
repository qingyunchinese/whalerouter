package com.android.whale.router.demo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.android.whale.base.component.BaseActivity
import com.whale.android.router.WhaleRouter
import com.whale.android.router.extension.navigate

class SplashActivity : BaseActivity() {

    private val handler: Handler by lazy {
        Handler()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler.postDelayed({
            WhaleRouter.build("home")
                .withBoolean("isDeepLink", true)
                .withString("fromPageName", "SplashPage")
                .addFlag(Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigate()
            finish()
        }, 3000)

    }
}