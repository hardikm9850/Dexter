/*
 * Created by Hardik on 30/12/23, 6:14 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 30/12/23, 6:14 pm
 *
 */

package com.hardik.dexter

import android.app.Application
import android.util.Log
import com.hardik.dexter.internal.logger.DexterLogger
import com.hardik.dexter.internal.tracker.ActivityTracker
import com.hardik.dexter.ui.ReportActivity
import java.lang.Thread.UncaughtExceptionHandler

internal class Dexter {

    private lateinit var application: Application
    private var enableDebugging: Boolean = false
    private var defaultExceptionHandler: UncaughtExceptionHandler? = null
    private lateinit var activityTracker: ActivityTracker

    private val TAG = Dexter::class.simpleName.orEmpty()

    fun setup(
        application: Application,
        defaultExceptionHandler: UncaughtExceptionHandler? = null,
        enableDebugging: Boolean = true,
    ) {
        this.application = application
        this.defaultExceptionHandler = defaultExceptionHandler
        this.enableDebugging = enableDebugging

        init()
    }

    private fun init() {
        DexterLogger.shouldEnableLogging(enableDebugging)
        DexterLogger.d(TAG, "Dexter is being initialised")

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
            showCrashInvestigationReport(Log.getStackTraceString(throwable))
            allowDefaultExceptionHandling(thread, throwable)
        }
    }

    private fun showCrashInvestigationReport(stacktrace: String) {
        val activityHistory = activityTracker.getActivityJourney()
        val fragmentHistory = activityTracker.getFragmentJourney()

        ReportActivity.getReportActivityInstance(
            application,
            stacktrace,
            activityHistory.reversed(),
            fragmentHistory.reversed(),
        )

        activityTracker = ActivityTracker(application)
    }

    private fun allowDefaultExceptionHandling(thread: Thread, throwable: Throwable) {
        defaultExceptionHandler?.let { handler ->
            handler::class.java.canonicalName?.apply {
                if (startsWith("com.google.firease.crashlytics").not()) {
                    DexterLogger.d(TAG, "Executing other exception handlers if any")
                    handler.uncaughtException(thread, throwable)
                }
            }
        }
    }
}
