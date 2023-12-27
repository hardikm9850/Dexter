/*
 * Created by Hardik on 27/12/23, 12:35 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 27/12/23, 12:35 pm
 *
 */

package com.hardik.dexter.internal.tracker

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.hardik.dexter.internal.model.ActivityInfo
import com.hardik.dexter.internal.model.FragmentInfo
import com.hardik.dexter.utils.ext.getDateForTimeStamp
import com.hardik.dexter.utils.ext.toKeyValuePair

class ActivityTracker constructor(private val application: Application) :
    FragmentLifecycleCallbacks() {
    private var recentActivity: Activity? = null

    private val activityList = mutableListOf<ActivityInfo>()
    private val fragmentList = mutableListOf<FragmentInfo>()

    init {
        trackActivityJourney()
    }

    private fun trackActivityJourney() {
        application.registerActivityLifecycleCallbacks(object :
            ActivityCallbacks() {
            override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityPreCreated(activity, savedInstanceState)
                if (activity is FragmentActivity) {
                    attachFragmentLifeCycleListener(activity)
                }
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                recentActivity = activity

                // we need to save the activity and related intent info
                activityList.add(
                    ActivityInfo(
                        activity = activity::class.java.simpleName,
                        bundleData = activity.intent.toKeyValuePair(),
                        createdTimeStamp = System.currentTimeMillis().getDateForTimeStamp(),
                    ),
                )
            }

            override fun onActivityDestroyed(activity: Activity) {
                if (activity is FragmentActivity) {
                    removeFragmentLifeCycleListener(activity)
                }
            }
        })
    }

    private fun removeFragmentLifeCycleListener(activity: FragmentActivity) {
        activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(this)
    }

    private fun attachFragmentLifeCycleListener(activity: FragmentActivity) {
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(this, false)
    }

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?,
    ) {
        fragmentList.add(
            FragmentInfo(
                fragmentName = f::class.java.simpleName,
                bundle = savedInstanceState?.toKeyValuePair().orEmpty(),
                timeStamp = System.currentTimeMillis().getDateForTimeStamp(),
            ),
        )
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
    }

    fun getActivityJourney() = activityList
    fun getFragmentJourney() = fragmentList
}
