package com.whale.android.router.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.whale.android.router.callback.NavigateCallback
import com.whale.android.router.meta.RouterResponse

class FragmentRouter : RouterComponent {

    override fun startComponent(
        context: Context,
        routerResponse: RouterResponse,
        directlyOpen: Boolean,
        callback: NavigateCallback?
    ) {
        try {
            val routeMapping = routerResponse.routerMapping
            routeMapping?.let {
                val routeRequest = routerResponse.request
                if (Fragment::class.java.isAssignableFrom(routeMapping.destination)) {
                    val fragment = Fragment.instantiate(
                        context,
                        routeMapping.destination.name,
                        routeRequest.extra
                    )
                    routerResponse.fragment = fragment
                } else {
                    routerResponse.fragment = null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw  e
        }
    }

}