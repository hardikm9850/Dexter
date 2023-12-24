/*
 * Created by Hardik on 24/12/23, 8:27 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 24/12/23, 8:27 pm
 *
 */

package com.hardik.dexter_example

import android.app.Application
import android.util.Log
import com.hardik.dexter_example.internal.logger.DexterLogger
import com.hardik.dexter_example.internal.tracker.ActivityTracker
import java.lang.Thread.UncaughtExceptionHandler
import java.time.Instant
import java.util.Arrays

internal class Dexter {

    private var isEnabled: Boolean = false
    private lateinit var application: Application
    private var enableDebugging: Boolean = false
    private var defaultExceptionHandler: UncaughtExceptionHandler? = null
    private lateinit var activityTracker: ActivityTracker

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
        setupActivityTracker()
    }

    private fun setupActivityTracker() {
        activityTracker = ActivityTracker(application)
    }

    private fun setUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            // On receiving the uncaught exception, we need to store in preference
            DexterLogger.e(TAG, "Exception received on thread $thread", throwable)
            val stacktrace = Arrays.toString(throwable.stackTrace).replace(',', '\n')
            showCrashInvestigationReport(
                stacktrace,
            )
            allowDefaultExceptionHandling(thread, throwable)
        }
    }

    private fun showCrashInvestigationReport(stacktrace: String) {
        Instant.now().epochSecond

        val activityHistory = activityTracker.getActivityJourney()
        val fragmentHistory = activityTracker.getActivityJourney()

        activityHistory.forEach {
            Log.d(TAG, it.toString())
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
