package com.whale.android.router.impl

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.whale.android.router.Constants
import com.whale.android.router.WhaleRouter
import com.whale.android.router.callback.NavigateCallback
import com.whale.android.router.meta.RouterResponse
import com.whale.android.router.utils.Utils

class ActivityRouter : RouterComponent {

    override fun startComponent(
        context: Context,
        routerResponse: RouterResponse,
        directlyOpen: Boolean,
        callback: NavigateCallback?
    ) {
        if (!directlyOpen) {
            return
        }
        val routeMapping = routerResponse.routerMapping!!
        val routeRequest = routerResponse.request
        val intent = Intent(context, routeMapping.destination)
        intent.putExtras(routeRequest.extra)
        // Set flags
        val flags = routeRequest.getFlags()
        if (-1 != flags) {
            intent.flags = flags
        } else if (context !is Activity) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        // Set Actions
        val action = routeRequest.getAction()
        action.let {
            intent.setAction(action)
        }
        try {
            startActivity(context, intent, routeRequest.getRequestCode())
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        callback?.arrived(routerResponse)
        routerResponse.routerPath().let {
            Utils.showDebugToast(context, "routerPath:$it arrived")
        }
    }

    private fun startActivity(
        context: Context,
        intent: Intent,
        requestCode: Int,
        extra: Bundle? = null,
        enterAnim: Int = -1,
        exitAnim: Int = -1
    ) {
        if (requestCode >= 0) {
            if (context is Activity) {
                ActivityCompat.startActivityForResult(context, intent, requestCode, extra)
            } else {
                WhaleRouter.logger.error(
                    Constants.GLOBALTAG,
                    "Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?"
                )
            }
        } else {
            ActivityCompat.startActivity(context, intent, extra)
        }

        if (-1 != enterAnim && -1 != exitAnim && context is Activity) {
            context.overridePendingTransition(enterAnim, exitAnim)
        }
    }

}