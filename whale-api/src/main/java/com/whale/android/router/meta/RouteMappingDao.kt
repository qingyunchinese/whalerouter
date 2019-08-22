package com.whale.android.router.meta

import com.whale.android.router.mapping.RouteMapping

class RouteMappingDao {

    private val routeMappingCollection = hashMapOf<String, RouteMapping>()

    fun addRouterMapping(routeMapping: RouteMapping) {
        val key = getRouteMappingKey(routeMapping)
        routeMappingCollection[key] = routeMapping
    }

    fun removeRouterMapping(routeMapping: RouteMapping) {
        val key = getRouteMappingKey(routeMapping)
        routeMappingCollection.remove(key)
    }

    fun queryRouterMapping(path: String): RouteMapping? {
        return routeMappingCollection[path]
    }

    private fun getRouteMappingKey(routeMapping: RouteMapping): String {
        return routeMapping.path
    }

}