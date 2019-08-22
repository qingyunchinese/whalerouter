package com.android.whale.router.demo

import android.app.Application
import com.whale.android.router.WhaleRouter
import com.whale.android.router.extension.navigate
import com.whale.android.router.impl.RouterAuthenticator
import com.whale.android.router.interceptor.RouterInterceptor
import com.whale.android.router.meta.RouterRequest
import com.whale.android.router.meta.RouterResponse

object WhaleRouterManager : RouterInterceptor, RouterAuthenticator {

    fun init(application: Application) {
        WhaleRouter.init(application, "whale")
        WhaleRouter.addRouteInterceptor(this)
        WhaleRouter.routerAuthenticator(this)
    }

    override fun intercept(chain: RouterInterceptor.Chain): RouterResponse {
        val routerRequest = chain.request()
        routerRequest.withBoolean("extra", true)
        val routerResponse = chain.proceed(routerRequest)
        return routerResponse
    }

    override fun authenticate(response: RouterResponse): RouterRequest? {
        return if (isAuthenticated()) {
            null
        } else {
            WhaleRouter.build("login")
        }
    }

    private fun isAuthenticated(): Boolean {
        return false
    }
}