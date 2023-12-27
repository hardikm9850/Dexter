/*
 * Created by Hardik on 27/12/23, 12:35 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 27/12/23, 12:35 pm
 *
 */

package com.hardik.dexter.utils.ext

import android.content.Intent
import android.os.Bundle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Intent.toKeyValuePair(): Map<String, String> {
    return this.extras?.toKeyValuePair() ?: emptyMap()
}

fun Bundle.toKeyValuePair(): Map<String, String> {
    val listOfBundleData = mutableMapOf<String, String>()
    val keySet = this.keySet()
    keySet?.forEach {
        listOfBundleData[it] = this.get(it).toString()
    }
    return listOfBundleData
}

fun Long.getDateForTimeStamp(): String {
    return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(this)).toString()
}
