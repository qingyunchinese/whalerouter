package com.whale.android.router.impl

import android.content.Context
import com.whale.android.router.callback.NavigateCallback
import com.whale.android.router.meta.RouterResponse

interface RouterComponent {

    fun startComponent(
        context: Context,
        routerResponse: RouterResponse,
        callback: NavigateCallback? = null
    )

}