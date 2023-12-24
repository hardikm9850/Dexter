/*
 * Created by Hardik on 24/12/23, 2:33 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 24/12/23, 2:33 pm
 *
 */

package com.hardik.dexter.internal.logger

import android.util.Log

internal object DexterLogger : ILogger {

    private var shouldEnableLogging: Boolean = false

    fun shouldEnableLogging(shouldEnableLogging: Boolean) {
        this.shouldEnableLogging = shouldEnableLogging
    }

    override fun d(tag: String, message: String) {
        if (shouldEnableLogging) {
            Log.d(tag, message)
        }
    }

    override fun i(tag: String, message: String) {
        if (shouldEnableLogging) {
            Log.i(tag, message)
        }
    }

    override fun w(tag: String, message: String, throwable: Throwable?) {
        if (shouldEnableLogging) {
            Log.w(tag, message, throwable)
        }
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        if (shouldEnableLogging) {
            Log.e(tag, message, throwable)
        }
    }
}
