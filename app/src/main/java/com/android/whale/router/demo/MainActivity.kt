package com.android.whale.router.demo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.whale.base.component.AppCompatToolbar
import com.android.whale.base.component.BaseActivity
import com.whale.android.router.WhaleRouter
import com.whale.android.router.WhaleService
import com.whale.android.router.annotation.Router
import com.whale.android.router.extension.instance
import com.whale.android.router.extension.navigate

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
        val loginMapping = WhaleService.query("login")
        loginMapping?.let {
            val routerMapping = it.cloneWithNewPath("sign")
            WhaleService.add(routerMapping)
        }
        WhaleRouter.build("sign").navigate(this)
    }

    /**
     * @see WhaleRouterManager.authenticate
     */
    fun routerParams(@Suppress("UNUSED_PARAMETER") view: View) {
        WhaleRouterManager.isAuthenticated = true
        WhaleRouter.build("detail")
            .withString("pageTitle", "WithParams")
            .withInt("id", 1)
            .withString("name", "Android")
            .withParcelable("WhaleProduct", WhaleProduct(1, "WhaleRouter", true))
            .withDouble("extraPrice", 9.68)
            .navigate(this)
    }

    /**
     * @see WhaleApplication.authenticate
     */
    fun routerHasRequiredParams(@Suppress("UNUSED_PARAMETER") view: View) {
        WhaleRouterManager.isAuthenticated = false
        WhaleRouter.build("detail")
            .withInt("id", 1)
            .withString("name", "Android")
            .withString("pageTitle", "CheckRouterParams")
            .navigate(this)
    }


    fun routerFragment(@Suppress("UNUSED_PARAMETER") view: View) {
        val routerResponse = WhaleRouter.build("setting/preference")
            .withBoolean("isVideo", false)
            .withLong("position", 123456).instance(this)
        routerResponse.fragment?.let {
            Toast.makeText(
                this,
                "Fragment:${it.javaClass.simpleName},Params:${it.arguments?.keySet()?.toList().toString()}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}