/*
 * Created by Hardik on 24/12/23, 2:54 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 24/12/23, 2:54 pm
 *
 */

package com.hardik.dexter

import android.app.Application
import com.hardik.dexter.internal.logger.DexterLogger
import java.lang.Thread.UncaughtExceptionHandler
import java.util.Arrays

object Dexter {

    private var isEnabled: Boolean = false
    private lateinit var application: Application
    private var enableDebugging: Boolean = false
    private var defaultExceptionHandler: UncaughtExceptionHandler? = null

    private val TAG = Dexter::class.simpleName.orEmpty()

    fun setup(
        application: Application,
        isEnabled: Boolean = true,
        defaultExceptionHandler: UncaughtExceptionHandler? = null,
        enableDebugging: Boolean = true,
    ) {
        this.application = application
        this.isEnabled = isEnabled
        this.defaultExceptionHandler = defaultExceptionHandler
        this.enableDebugging = enableDebugging

        init()
    }

    private fun init() {
        DexterLogger.shouldEnableLogging(enableDebugging)
        setUncaughtExceptionHandler()
    }

    private fun setUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            // On receiving the uncaught exception, we need to store in preference
            DexterLogger.e(TAG, "Exception received on thread $thread", throwable)
            val stacktrace = Arrays.toString(throwable.stackTrace).replace(',', '\n')

            allowDefaultExceptionHandling(thread, throwable)
        }
    }

    private fun allowDefaultExceptionHandling(thread: Thread, throwable: Throwable) {
        defaultExceptionHandler?.let { handler ->
            handler::class.java.canonicalName?.apply {
                if (startsWith("com.google.firease.crashlytics").not()) {
                    handler.uncaughtException(thread, throwable)
                }
            }
        }
    }
}
