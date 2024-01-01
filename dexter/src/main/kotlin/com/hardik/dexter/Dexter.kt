/*
 * Created by Hardik on 01/01/24, 7:04 pm
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 01/01/24, 7:04 pm
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
            prepareCrashReport(throwable)
            allowDefaultExceptionHandling(thread, throwable)
        }
    }

    private fun prepareCrashReport(throwable: Throwable) {
        val deviceInfo = collectDeviceInfo()
        val stacktrace = Log.getStackTraceString(throwable)
        val logInfo = buildString {
            append("===== Device Information =====\n")
            append(deviceInfo)
            append("\n===== Stacktrace =====\n")
            append(stacktrace + "\n")
        }
        showCrashInvestigationReport(logInfo)
    }

    private fun collectDeviceInfo(): String {
        return buildString {
            append("OS version = ${System.getProperty("os.version")}\n") // OS version
            append("SDK version = ${android.os.Build.VERSION.RELEASE}\n") // API Level
            append("Device =  ${android.os.Build.DEVICE}\n")
            append("Model = ${android.os.Build.MODEL}\n")
            append("Product name = ${android.os.Build.PRODUCT}\n")
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
