package com.whale.android.router.processor

import javax.annotation.processing.Messager
import javax.tools.Diagnostic

class GradleLogger(private val logMessager: Messager, private val debug: Boolean = false) {

    fun errorMessage(format: String, vararg args: Any) {
        logMessager.printMessage(Diagnostic.Kind.ERROR, String.format(format, *args))
    }

    fun debugMessage(format: String, vararg args: Any) {
        if (debug) {
            logMessager.printMessage(Diagnostic.Kind.NOTE, String.format(format, *args))
        }

    }

}