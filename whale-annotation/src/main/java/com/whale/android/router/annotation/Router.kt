package com.whale.android.router.annotation

import kotlin.annotation.*

/**
 * inject a route map to activity
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.BINARY)
annotation class Router(
    val path: Array<String>,
    val description: String="",
    val requiredParams: Array<String> = [],
    val requiredAuthor: Boolean = false
)
