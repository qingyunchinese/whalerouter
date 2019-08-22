package com.whale.android.router.meta

import com.whale.android.router.mapping.RouteMapping
import java.lang.StringBuilder

class RouterResponse(val request: RouterRequest, val routerMapping: RouteMapping? = null) {

    var statues: Int = ARRIVED
    var missMatchParams = arrayListOf<String>()

    fun getMisMatchParams(): String {
        val misMatchParams = StringBuilder()
        missMatchParams.forEach {
            if (misMatchParams.isEmpty()) {
                misMatchParams.append(it)
            } else {
                misMatchParams.append(",$it")
            }

        }
        return misMatchParams.toString()
    }

    companion object {
        val LOST = 500
        val ARRIVED = 200
        val BAD_PARAMS = 400
        val UNAUTHORIZED = 401
    }

    fun getComponent() = routerMapping?.destination

    fun routerType() = routerMapping?.routeComponentType

    fun routerPath() = routerMapping?.path
}