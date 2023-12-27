/*
 * Created by Hardik on 27/12/23, 12:35 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 27/12/23, 12:35 pm
 *
 */

package com.hardik.dexter.internal.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FragmentInfo(
    val fragmentName: String,
    val bundle: Map<String, String>,
    val timeStamp: String,
) :
    Parcelable
