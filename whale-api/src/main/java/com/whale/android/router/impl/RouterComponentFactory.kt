package com.whale.android.router.impl

import com.whale.android.router.mapping.RouterType

class RouterComponentFactory {

    private val cacheRouterComponent = hashMapOf<String, RouterComponent>()

    fun getRouterComponent(routerComponentType: String): RouterComponent? {
        return when (routerComponentType) {
            RouterType.ACTIVITY -> {
                if (!cacheRouterComponent.containsKey(routerComponentType)) {
                    cacheRouterComponent[routerComponentType] = ActivityRouter()
                }
                cacheRouterComponent[routerComponentType]
            }
            RouterType.FRAGMENT -> {
                null
            }
            RouterType.SERVICE -> {
                if (!cacheRouterComponent.containsKey(routerComponentType)) {
                    cacheRouterComponent[routerComponentType] = ServiceRouter()
                }
                return ServiceRouter()
            }
            RouterType.CONTENT_PROVIDER -> {
                null
            }
            else -> {
                null
            }
        }
    }

}