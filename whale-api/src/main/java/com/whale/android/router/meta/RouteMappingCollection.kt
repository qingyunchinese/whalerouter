package com.whale.android.router.meta

import com.whale.android.router.mapping.RouteMapping

class RouteMappingCollection {

    private val routeMappingCollection = hashMapOf<String, RouteMapping>()

    fun addRouteMapping(routeMapping: RouteMapping) {
        val key = getRouteMappingKey(routeMapping)
        routeMappingCollection[key] = routeMapping
    }

    fun removeRouteMapping(routeMapping: RouteMapping) {
        val key = getRouteMappingKey(routeMapping)
        routeMappingCollection.remove(key)
    }

    fun queryRouteMapping(path: String): RouteMapping? {
        return routeMappingCollection[path]
    }

    private fun getRouteMappingKey(routeMapping: RouteMapping): String {
        return routeMapping.path
    }

}