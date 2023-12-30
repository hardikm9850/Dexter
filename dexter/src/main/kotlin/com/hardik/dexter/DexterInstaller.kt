/*
 * Created by Hardik on 30/12/23, 6:14 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 30/12/23, 6:14 pm
 *
 */

package com.hardik.dexter

import android.app.Application
import android.util.Log

class DexterInstaller {
    companion object {
        private var dexterInstance: Dexter? = null
        private var lock = Any()

        /**
         * Creates a singleton Dexter instance
         * @param application application instance
         * @param isEnabled should enable Dexter
         * @param defaultExceptionHandler if the client wants to capture the global exception
         * @param enableDebugging if the client wants to view the logs
         */
        fun setup(
            application: Application,
            defaultExceptionHandler: Thread.UncaughtExceptionHandler? = null,
            enableDebugging: Boolean = true,
        ) {
            synchronized(lock) {
                if (dexterInstance == null) {
                    dexterInstance = Dexter().also {
                        it.setup(
                            application,
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
