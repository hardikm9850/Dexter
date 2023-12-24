/*
 * Created by Hardik on 24/12/23, 8:34 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 24/12/23, 8:34 pm
 *
 */

package com.hardik.dexter.internal.model

import android.content.Intent

data class ActivityInfo(val activity: String, val intent: Intent, val createdTimeStamp: Long)
