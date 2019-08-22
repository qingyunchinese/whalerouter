package com.android.whale.router.demo

import android.os.Bundle
import com.android.whale.base.component.AppCompatToolbar
import com.android.whale.base.component.BaseActivity
import com.whale.android.router.annotation.Router
import com.whale.android.router.annotation.RouterField
import kotlinx.android.synthetic.main.activity_product_detail.*

@Router(path = ["detail"], requiredParams = ["id", "name"], requiredAuthor = true)
class ProductDetailActivity : BaseActivity() {

    @RouterField(paramsName = "name")
    var pageTitle: String = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_product_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getRouterPageTitle()?.let {
            setTitle(it)
        }
        setNavigationIcon(AppCompatToolbar.NAVIGATION_BACK)
        intentParams.text = getIntentParams()
    }

    private fun getIntentParams(): String {
        val paramsKey = StringBuilder()
        intent.extras?.keySet()?.forEach {
            paramsKey.append("$it\n")
        }
        return paramsKey.toString()
    }
}