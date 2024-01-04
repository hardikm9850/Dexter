package com.hardik.dexter.utils

import android.util.Log

object EvidenceManager {
    /**
     * This will assist Dexter in compiling the necessary evidence for the forensic report.
     */
    fun getCrimeEvidence(throwable: Throwable): String {
        val deviceInfo = collectDeviceInfo()
        val stacktrace = Log.getStackTraceString(throwable)
        return buildString {
            append("===== Device Information =====\n")
            append(deviceInfo)
            append("\n===== Stacktrace =====\n")
            append(stacktrace + "\n")
        }
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
}