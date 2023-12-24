/*
 * Created by Hardik on 24/12/23, 8:27 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 24/12/23, 8:27 pm
 *
 */

package com.hardik.dexter_example

import android.app.Application

class DexterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DexterInstaller.setup(
            this,
        )
    }
}
