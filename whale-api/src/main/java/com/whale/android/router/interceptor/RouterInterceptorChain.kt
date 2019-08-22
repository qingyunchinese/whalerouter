package com.whale.android.router.interceptor

import com.whale.android.router.meta.RouterRequest
import com.whale.android.router.meta.RouterResponse

class RouterInterceptorChain(
    private val routerInterceptors: List<RouterInterceptor>,
    private val routerRequest: RouterRequest,
    private val chainIndex: Int

) : RouterInterceptor.Chain {

    override fun proceed(request: RouterRequest): RouterResponse {
        if (chainIndex > routerInterceptors.size) {
            return RouterResponse(request)
        }
        val next = RouterInterceptorChain(
            routerInterceptors, request,
            chainIndex + 1
        )
        val interceptor = routerInterceptors[chainIndex]
        return interceptor.intercept(next)
    }

    override fun request(): RouterRequest = routerRequest
}