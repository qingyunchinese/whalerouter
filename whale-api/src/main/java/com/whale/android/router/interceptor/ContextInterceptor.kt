package com.whale.android.router.interceptor

import android.content.Context
import com.whale.android.router.meta.RouterRequest
import com.whale.android.router.meta.RouterResponse

interface ContextInterceptor {
    fun intercept(
        context: Context?,
        routerRequest: RouterRequest,
        routerResponse: RouterResponse
    ): Context? {
        return context
    }
}