package com.android.whale.router.api.interceptor

import com.whale.android.router.meta.WRouteMeta

interface RouterInterceptor {

    fun interceptorRoute(path: String, group: String, routeMeta: WRouteMeta): Boolean = false

}