package com.android.whale.router.demo

import android.os.Bundle
import android.view.View
import com.android.whale.base.component.AppCompatToolbar
import com.android.whale.base.component.BaseActivity
import com.whale.android.router.WhaleRouter
import com.whale.android.router.annotation.Router

@Router(path = ["home"])
class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(resources.getString(R.string.main))
        setNavigationIcon(AppCompatToolbar.NAVIGATION_MENU)
    }

    fun authComponent(@Suppress("UNUSED_PARAMETER") view: View) {
        WhaleRouter.build("login").navigate(this)
    }

    fun settingComponent(@Suppress("UNUSED_PARAMETER") view: View) {
        WhaleRouter.build("setting").navigate(this)
    }

    fun dynamicLogin(@Suppress("UNUSED_PARAMETER") view: View) {
        val loginMapping = WhaleRouter.findRouteMapping("login")
        loginMapping?.let {
            val routerMapping = it.cloneWithNewPath("sign")
            WhaleRouter.addRouterMapping(routerMapping)
        }
        WhaleRouter.build("sign").navigate(this)
    }

    fun routerParams(@Suppress("UNUSED_PARAMETER") view: View) {
        WhaleRouter.build("detail")
            .withBoolean("access", true)
            .withString("name", "WhaleRouter")
            .withInt("id", 1)
            .withParcelable("WhaleProduct", WhaleProduct(1, "WhaleRouter", true))
            .withDouble("extraPrice", 9.68)
            .navigate(this)
    }
}