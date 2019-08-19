package com.whale.android.router.annotation

import kotlin.annotation.*

/**
 *  inject Activity variable value with route same key
 */
@MustBeDocumented
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.BINARY)
annotation class RouterField(val paramsName: String = "", val transforClassName: String = "")