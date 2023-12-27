/*
 * Created by Hardik on 27/12/23, 11:05 am
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 27/12/23, 11:05 am
 *
 */

package com.hardik.dexter.internal.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ActivityInfo(val activity: String, val bundleData: Map<String, String>, val createdTimeStamp: String) : Parcelable
