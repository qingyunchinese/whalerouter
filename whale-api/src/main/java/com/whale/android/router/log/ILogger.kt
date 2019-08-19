package com.whale.android.router.log

interface ILogger {

    fun verbose(tag: String, message: String)

    fun debug(tag: String, message: String)

    fun info(tag: String, message: String)

    fun warning(tag: String, message: String)

    fun error(tag: String, message: String)
}