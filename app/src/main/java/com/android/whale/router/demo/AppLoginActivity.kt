package com.android.whale.router.demo

import android.os.Bundle
import com.android.whale.base.component.AppCompatToolbar
import com.android.whale.base.component.BaseActivity

class AppLoginActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_app_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(resources.getString(R.string.login))
        setNavigationIcon(AppCompatToolbar.NAVIGATION_BACK)
    }
}