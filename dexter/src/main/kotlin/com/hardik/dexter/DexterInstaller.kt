/*
 * Created by Hardik on 24/12/23, 9:13 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 24/12/23, 9:13 pm
 *
 */

package com.hardik.dexter

import android.app.Application
import android.util.Log

class DexterInstaller {
    companion object {
        private var dexterInstance: Dexter? = null
        private var lock = Any()

        fun setup(
            application: Application,
            isEnabled: Boolean = true,
            defaultExceptionHandler: Thread.UncaughtExceptionHandler? = null,
            enableDebugging: Boolean = true,
        ) {
            synchronized(lock) {
                if (dexterInstance == null) {
                    dexterInstance = Dexter().also {
                        it.setup(
                            application,
                            isEnabled,
                            defaultExceptionHandler,
                            enableDebugging,
                        )
                    }
                    return@synchronized
                }
                Log.e(
                    Dexter::class.simpleName,
                    "Dexter is already installed. No need to setup Dexter again.",
                )
            }
        }
    }
}
