package com.android.whale.router.demo

fun ProductDetailActivity.getRouterPageTitle(): String? {
    return intent.extras?.getString("pageTitle","")
}