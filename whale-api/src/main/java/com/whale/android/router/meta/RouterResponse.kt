package com.whale.android.router.meta

import androidx.fragment.app.Fragment
import com.whale.android.router.mapping.RouteMapping

class RouterResponse(val request: RouterRequest, val routerMapping: RouteMapping? = null) {

    var statues: Int = ARRIVED
    var missMatchParams = arrayListOf<String>()
    var fragment: Fragment? = null

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
    }

    fun routerType() = routerMapping?.routeComponentType

    fun routerPath() = routerMapping?.path
}