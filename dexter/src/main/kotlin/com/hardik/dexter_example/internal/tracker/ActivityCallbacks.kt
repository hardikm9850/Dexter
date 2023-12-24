/*
 * Created by Hardik on 24/12/23, 8:27 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 24/12/23, 8:27 pm
 *
 */

package com.hardik.dexter_example.internal.tracker

import android.app.Activity
import android.app.Application
import android.os.Bundle

abstract class ActivityCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }
}
