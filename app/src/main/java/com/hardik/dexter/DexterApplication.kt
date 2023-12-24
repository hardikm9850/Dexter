/*
 * Created by Hardik on 24/12/23, 2:54 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 24/12/23, 2:54 pm
 *
 */

package com.hardik.dexter

import android.app.Application

class DexterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Dexter.setup(
            this,
        )
    }
}
