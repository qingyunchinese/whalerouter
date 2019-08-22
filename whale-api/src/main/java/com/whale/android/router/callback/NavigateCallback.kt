package com.whale.android.router.callback


import com.whale.android.router.meta.RouterResponse

interface NavigateCallback {

    fun notFound()

    fun arrived(routerResponse: RouterResponse)

    fun error(e: Throwable)
}