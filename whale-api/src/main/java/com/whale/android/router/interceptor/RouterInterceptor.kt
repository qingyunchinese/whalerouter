package com.whale.android.router.interceptor

import com.whale.android.router.meta.RouterRequest
import com.whale.android.router.meta.RouterResponse

interface RouterInterceptor {

    fun intercept(chain: Chain): RouterResponse

    interface Chain {
        fun proceed(request: RouterRequest): RouterResponse

        fun request(): RouterRequest
    }
}