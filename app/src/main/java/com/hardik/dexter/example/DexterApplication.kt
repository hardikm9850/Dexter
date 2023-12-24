/*
 * Created by Hardik on 24/12/23, 8:34 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 24/12/23, 8:34 pm
 *
 */

package com.hardik.dexter.example

import android.app.Application
import com.hardik.dexter.DexterInstaller

class DexterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DexterInstaller.setup(
            this,
        )
    }
}
