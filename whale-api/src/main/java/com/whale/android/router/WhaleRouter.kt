package com.whale.android.router

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import com.android.whale.router.api.interceptor.NavigateCallback
import com.android.whale.router.api.interceptor.RouterInterceptor
import com.whale.android.router.log.AndroidLogger
import com.whale.android.router.log.ILogger
import com.whale.android.router.meta.RouteMappingCollection
import com.whale.android.router.meta.WRouteMeta
import com.whale.android.router.api.BuildConfig
import com.whale.android.router.mapping.RouteMapping
import com.whale.android.router.mapping.RouterMappingImpl


@SuppressLint("StaticFieldLeak")
object WhaleRouter {

    private val routeInterceptors: MutableList<RouterInterceptor> = mutableListOf()
    @Volatile
    private var hasInit = false
    private lateinit var globeContext: Context
    private lateinit var handler: Handler
    private lateinit var appSchema: String
    private lateinit var routerMappingImpl: RouterMappingImpl
    private var logger: ILogger =
        AndroidLogger(BuildConfig.DEBUG)
    private val routeMappingCollection: RouteMappingCollection by lazy {
        RouteMappingCollection()
    }

    @Synchronized
    fun init(application: Application, schema: String): Boolean {
        appSchema = schema
        globeContext = application
        routerMappingImpl = initRouterMapping()
        hasInit = true
        handler = Handler(Looper.getMainLooper())
        return true
    }

    fun build(routerPath: String): WRouteMeta {
        if (TextUtils.isEmpty(routerPath)) {
            throw NullPointerException("routerPath Parameter is invalid!")
        } else {
            return WRouteMeta(routerPath)
        }
    }

    fun addRouteInterceptor(routeInterceptor: RouterInterceptor) = apply {
        routeInterceptors += routeInterceptor
    }

    fun removeRouteInterceptor(routeInterceptor: RouterInterceptor) {
        routeInterceptors.remove(routeInterceptor)
    }

    fun networkInterceptors(): MutableList<RouterInterceptor> = routeInterceptors

    fun navigate(
        wRouteMeta: WRouteMeta,
        requestCode: Int = -1,
        context: Context? = null,
        callback: NavigateCallback? = null
    ) {
        val currentContext = context ?: globeContext
        val routePath = wRouteMeta.routerPath
        val routeMapping = findRouteMapping(wRouteMeta.routerPath)
        if (routeMapping != null) {

            val intent = Intent(currentContext, routeMapping.destination)
            intent.putExtras(wRouteMeta.extra)

            // Set flags.
            val flags = wRouteMeta.getFlags()
            if (-1 != flags) {
                intent.flags = flags
            } else if (currentContext !is Activity) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            // Set Actions
            val action = wRouteMeta.getAction()
            action?.let {
                intent.setAction(action)
            }

            // Navigation in main looper.
            runInMainThread(Runnable {
                startActivity(
                    requestCode,
                    currentContext,
                    intent,
                    wRouteMeta,
                    callback
                )
            })

        } else {
            logger.error(
                Constants.GLOBALTAG,
                "not found page by routePath=$routePath"
            )
        }
    }

    private fun startActivity(
        requestCode: Int,
        context: Context,
        intent: Intent,
        wRouteMeta: WRouteMeta,
        callback: NavigateCallback?
    ) {
        if (requestCode >= 0) {
            if (context is Activity) {
                ActivityCompat.startActivityForResult(context, intent, requestCode, wRouteMeta.extra)
            } else {
                logger.error(
                    Constants.GLOBALTAG,
                    "Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?"
                )
            }
        } else {
            ActivityCompat.startActivity(context, intent, wRouteMeta.extra)
        }

        if (-1 != wRouteMeta.getEnterAnim() && -1 != wRouteMeta.getExitAnim() && context is Activity) {
            context.overridePendingTransition(wRouteMeta.getEnterAnim(), wRouteMeta.getExitAnim())
        }

        callback?.let {
            callback.afterNavigate(context, wRouteMeta)
        }
    }

    private fun runInMainThread(runnable: Runnable) {
        if (Looper.getMainLooper().thread !== Thread.currentThread()) {
            handler.post(runnable)
        } else {
            runnable.run()
        }
    }

    fun findRouteMapping(path: String): RouteMapping? {
        return routeMappingCollection.queryRouteMapping(path)
    }

    fun addRouterMapping(routeMapping: RouteMapping) {
        routeMappingCollection.addRouteMapping(routeMapping)
    }

    private fun initRouterMapping(): RouterMappingImpl {
        val finder = Class.forName("com.whale.android.router.WhaleRouterMapping")
        val routerInit = finder.newInstance() as RouterMappingImpl
        routerInit.initRouter()
        return routerInit
    }
}