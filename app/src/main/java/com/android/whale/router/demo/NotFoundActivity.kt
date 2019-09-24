package com.android.whale.router.demo

import android.os.Bundle
import com.android.whale.base.component.AppCompatToolbar
import com.android.whale.base.component.BaseActivity
import com.whale.android.router.annotation.Router

@Router(path = ["notfound","404"],description = "xxx")
class NotFoundActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_not_found
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(resources.getString(R.string.not_found))
        setNavigationIcon(AppCompatToolbar.NAVIGATION_BACK)
    }
}