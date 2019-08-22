package com.whale.android.router

import com.whale.android.router.mapping.RouteMapping
import com.whale.android.router.mapping.RouterMappingImpl
import com.whale.android.router.meta.RouteMappingDao

object WhaleService {

    private val routeMappingDao: RouteMappingDao by lazy {
        RouteMappingDao()
    }

    fun startService() {
        initRouterMappingDao()
    }

    private fun initRouterMappingDao(): RouterMappingImpl {
        val finder = Class.forName("com.whale.android.router.WhaleRouterMapping")
        val routerInit = finder.newInstance() as RouterMappingImpl
        routerInit.initRouter()
        return routerInit
    }

    fun query(path: String): RouteMapping? {
        return routeMappingDao.queryRouterMapping(path)
    }

    fun add(routeMapping: RouteMapping) {
        return routeMappingDao.addRouterMapping(routeMapping)
    }

    fun delete(routeMapping: RouteMapping) {
        return routeMappingDao.removeRouterMapping(routeMapping)
    }
}