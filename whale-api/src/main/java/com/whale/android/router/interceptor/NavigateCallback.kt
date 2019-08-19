package com.android.whale.router.api.interceptor

import android.content.Context
import com.whale.android.router.meta.WRouteMeta

interface NavigateCallback {

    fun notFound(context: Context, routerMeta: WRouteMeta)

    fun beforeNavigate(context: Context, routerMeta: WRouteMeta): Boolean

    fun afterNavigate(context: Context, routerMeta: WRouteMeta) {

    }

    fun error(context: Context, routerMeta: WRouteMeta, e: Throwable) {

    }
}