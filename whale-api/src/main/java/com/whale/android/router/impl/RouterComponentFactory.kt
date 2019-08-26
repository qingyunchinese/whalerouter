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
                if (!cacheRouterComponent.containsKey(routerComponentType)) {
                    cacheRouterComponent[routerComponentType] = FragmentRouter()
                }
                cacheRouterComponent[routerComponentType]
            }
            else -> {
                null
            }
        }
    }

}