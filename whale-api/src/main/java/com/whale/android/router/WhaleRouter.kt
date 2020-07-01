package com.whale.android.router

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import com.whale.android.router.callback.NavigateCallback
import com.whale.android.router.impl.RouterAuthenticator
import com.whale.android.router.impl.RouterComponentFactory
import com.whale.android.router.interceptor.RealRouterInterceptor
import com.whale.android.router.interceptor.RouterInterceptor
import com.whale.android.router.interceptor.RouterInterceptorChain
import com.whale.android.router.log.AndroidLogger
import com.whale.android.router.log.ILogger
import com.whale.android.router.meta.RouterRequest
import com.whale.android.router.meta.RouterResponse
import com.whale.android.router.utils.Utils


@SuppressLint("StaticFieldLeak")
object WhaleRouter {

    private val routeInterceptors: MutableList<RouterInterceptor> = mutableListOf()
    private var authenticator: RouterAuthenticator = RouterAuthenticator.NONE
    @Volatile
    private var hasInit = false
    private lateinit var globeContext: Context
    private lateinit var handler: Handler
    private lateinit var appSchema: String
    var debugMode: Boolean = false

    lateinit var logger: ILogger

    private val routerComponentFactory: RouterComponentFactory by lazy {
        RouterComponentFactory()
    }

    @Synchronized
    fun init(application: Application, schema: String, debug: Boolean = false): Boolean {
        appSchema = schema
        globeContext = application
        hasInit = true
        debugMode = debug
        logger = AndroidLogger(debugMode)
        handler = Handler(Looper.getMainLooper())
        WhaleService.startService()
        return true
    }

    fun build(routerPath: String): RouterRequest {
        if (TextUtils.isEmpty(routerPath)) {
            throw NullPointerException("routerPath Parameter is invalid!")
        } else {
            return RouterRequest(routerPath)
        }
    }

    fun addRouteInterceptor(routeInterceptor: RouterInterceptor) = apply {
        routeInterceptors += routeInterceptor
    }

    @Suppress
    fun removeRouteInterceptor(routeInterceptor: RouterInterceptor) = apply {
        routeInterceptors.remove(routeInterceptor)
    }

    @Suppress
    fun routerAuthenticator(routerAuthenticator: RouterAuthenticator) = apply {
        authenticator = routerAuthenticator
    }

    fun routerAuthenticator() = authenticator

    @Suppress
    fun routerInterceptors(): MutableList<RouterInterceptor> = routeInterceptors

    fun navigate(
        context: Context?,
        routerRequest: RouterRequest,
        callback: NavigateCallback? = null
    ) {
        runInMainThread(Runnable {
            try {
                executeRouterRequest(context, routerRequest, true, callback)
            } catch (e: Exception) {
                e.printStackTrace()
                callback?.error(e)
            }
        })
    }

    private fun executeRouterRequest(
        context: Context?,
        routerRequest: RouterRequest,
        directlyOpen: Boolean = true,
        callback: NavigateCallback?
    ): RouterResponse {
        val realContext = context ?: globeContext
        val routerInterceptorChain = getRouterInterceptorChain(routerRequest)
        val routerResponse = routerInterceptorChain.proceed(request = routerRequest)
        when (routerResponse.statues) {
            RouterResponse.LOST -> {
                routerResponse.routerPath().let {
                    Utils.showDebugToast(globeContext, "routerPath:$it lost")
                }
                callback?.notFound()
            }
            RouterResponse.ARRIVED -> {
                proceedComponent(realContext, routerResponse,directlyOpen, callback)
            }
            RouterResponse.BAD_PARAMS -> {
                routerResponse.routerPath().let {
                    Utils.showDebugToast(
                        globeContext,
                        "routerPath:$it not match requiredParams:${routerResponse.getMisMatchParams()}"
                    )
                }
                callback?.notFound()
            }
        }
        return routerResponse
    }

    private fun proceedComponent(
        context: Context,
        routerResponse: RouterResponse,
        directlyOpen: Boolean = true,
        callback: NavigateCallback?
    ) {
        val routerComponentType = routerResponse.routerType()!!
        val routerComponent = routerComponentFactory.getRouterComponent(routerComponentType)
        routerComponent?.startComponent(context, routerResponse, directlyOpen,callback)
    }

    private fun getRouterInterceptorChain(routerRequest: RouterRequest): RouterInterceptorChain {
        val routeInterceptors = mutableListOf<RouterInterceptor>()
        routeInterceptors.addAll(routerInterceptors())
        routeInterceptors += RealRouterInterceptor()
        return RouterInterceptorChain(routeInterceptors, routerRequest, 0)
    }


    private fun runInMainThread(runnable: Runnable) {
        if (Looper.getMainLooper().thread !== Thread.currentThread()) {
            handler.post(runnable)
        } else {
            runnable.run()
        }
    }

    fun instance(
        context: Context?,
        routerRequest: RouterRequest,
        callback: NavigateCallback? = null
    ): RouterResponse {
        val realContext = context ?: globeContext
        return executeRouterRequest(realContext, routerRequest, false, callback)
    }
}