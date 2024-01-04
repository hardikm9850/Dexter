/*
 * Created by Hardik on 01/01/24, 7:04 pm
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 01/01/24, 7:04 pm
 *
 */

package com.hardik.dexter

import android.app.Application
import com.hardik.dexter.internal.logger.DexterLogger
import com.hardik.dexter.internal.tracker.ActivityTracker
import java.lang.Thread.UncaughtExceptionHandler
import kotlin.system.exitProcess
import android.os.Process
import com.hardik.dexter.internal.model.ActivityInfo
import com.hardik.dexter.internal.model.FragmentInfo
import com.hardik.dexter.utils.EvidenceManager
import com.hardik.dexter.utils.ReportCreator
import com.hardik.dexter.utils.ext.TAG

internal class Dexter {

    private var enableDebugging: Boolean = false
    private var defaultExceptionHandler: ((Throwable) -> Unit)? = null
    private val listOfDefaultExceptionHandlers = mutableListOf<UncaughtExceptionHandler>()
    private lateinit var activityTracker: ActivityTracker
    private lateinit var application: Application

    fun setup(
        application: Application,
        uncaughtExceptionHandler: ((Throwable) -> Unit)? = null,
        enableDebugging: Boolean = true,
    ) {
        this.application = application
        this.defaultExceptionHandler = uncaughtExceptionHandler
        this.enableDebugging = enableDebugging

        init()
    }

    private fun init() {
        DexterLogger.shouldEnableLogging(enableDebugging)
        DexterLogger.d(TAG, "Dexter is being initialised")

        addUserDefinedUncaughtExceptionHandler()
        setUncaughtExceptionHandler()
        setupActivityTracker()
    }

    private fun setupActivityTracker() {
        activityTracker = ActivityTracker(application)
    }

    private fun setUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(CrimeEventListener())
    }

    /**
     * This takes care of handling any other crash monitoring libs such as
     * 1. Firebase crashlytics
     * 2. User provided exception handling lambda [defaultExceptionHandler]
     */
    private fun addUserDefinedUncaughtExceptionHandler() {
        defaultExceptionHandler?.let {
            listOfDefaultExceptionHandlers.add(UncaughtExceptionHandler { t, e ->
                it.invoke(e)
            })
        }
        Thread.getDefaultUncaughtExceptionHandler()?.let {
            listOfDefaultExceptionHandlers.add(it)
        }
    }

    /**
     * Provide a summary of the events that preceded the criminal activity
     */
    private fun getRecapOfEvents(): Pair<MutableList<ActivityInfo>, MutableList<FragmentInfo>> {
        val activityHistory = activityTracker.getActivityJourney()
        val fragmentHistory = activityTracker.getFragmentJourney()
        return Pair(activityHistory,fragmentHistory)
    }

    private fun allowDefaultExceptionHandling(thread: Thread, throwable: Throwable) {
        listOfDefaultExceptionHandlers.forEach { handler ->
            try {
                handler.uncaughtException(thread, throwable)
            } catch (e: Exception) {
                DexterLogger.e(TAG, "Exception occurred while iterating over default handlers", e)
            }
        }
    }

    inner class CrimeEventListener : UncaughtExceptionHandler {

        override fun uncaughtException(thread: Thread, throwable: Throwable) {
            try {
                DexterLogger.e(TAG, "A new crime has been reported", throwable)
                DexterLogger.d(TAG,"===== Autopsy will now be performed =====")
                val crimeEvidence = EvidenceManager.getCrimeEvidence(throwable)
                val (activityHistory, fragmentHistory) = getRecapOfEvents()
                ReportCreator.prepareEvidenceReport(
                    application = application,
                    stacktrace = crimeEvidence,
                    activityHistory = activityHistory,
                    fragmentHistory = fragmentHistory
                )
                allowDefaultExceptionHandling(thread, throwable)
            } catch (e: Exception) {
                DexterLogger.e(TAG, "Exception occurred while analysing the evidences", e)
            } finally {
                activityTracker = ActivityTracker(application)
                Process.killProcess(Process.myPid())
                exitProcess(2)
            }
        }
    }
}