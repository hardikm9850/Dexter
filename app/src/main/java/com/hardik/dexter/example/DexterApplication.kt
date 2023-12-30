/*
 * Created by Hardik on 30/12/23, 6:20 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 30/12/23, 6:20 pm
 *
 */

package com.hardik.dexter.example

import android.app.Application
import com.hardik.dexter.DexterInstaller

class DexterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DexterInstaller.setup(
            application = this,
            defaultExceptionHandler = { thread, throwable ->
                // No-op
            },
        )
    }
}
