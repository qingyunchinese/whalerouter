package com.whale.android.router.interceptor

import com.whale.android.router.WhaleRouter
import com.whale.android.router.WhaleService
import com.whale.android.router.mapping.RouteMapping
import com.whale.android.router.meta.RouterRequest
import com.whale.android.router.meta.RouterResponse

class RealRouterInterceptor : RouterInterceptor {

    override fun intercept(chain: RouterInterceptor.Chain): RouterResponse {
        val routerRequest = chain.request()
        val routeMapping = WhaleService.query(routerRequest.routerPath)
        return if (routeMapping == null) {
            val routerResponse = RouterResponse(routerRequest)
            routerResponse.statues = RouterResponse.LOST
            routerResponse
        } else {
            responseRouterRequest(routerRequest, routeMapping)
        }
    }

    private fun responseRouterRequest(
        routerRequest: RouterRequest,
        routeMapping: RouteMapping
    ): RouterResponse {
        val routerResponse = RouterResponse(routerRequest, routeMapping)
        val requiredAuthor = routeMapping.requiredAuthor
        if (requiredAuthor) {
            val request = WhaleRouter.routerAuthenticator().authenticate(routerResponse)
            if (request != null) {
                val authorRouterMapping = WhaleService.query(request.routerPath)
                if (authorRouterMapping != null) {
                    return RouterResponse(request, authorRouterMapping)
                }
            }
        }
        val requiredParams = routeMapping.requiredParams
        if (requiredParams.isEmpty()) {
            routerResponse.statues = RouterResponse.ARRIVED
        } else {
            val notFoundRequiredParams = arrayListOf<String>()
            requiredParams.forEach {
                if (!routerRequest.extra.containsKey(it)) {
                    notFoundRequiredParams.add(it)
                }
            }
            if (notFoundRequiredParams.isNotEmpty()) {
                routerResponse.missMatchParams = notFoundRequiredParams
                routerResponse.statues = RouterResponse.BAD_PARAMS
            } else {
                routerResponse.statues = RouterResponse.ARRIVED
            }
        }
        return routerResponse
    }
}