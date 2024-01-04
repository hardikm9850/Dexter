/*
 * Created by Hardik on 01/01/24, 6:47 pm
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 01/01/24, 6:47 pm
 *
 */

package com.hardik.dexter.utils.ext

import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val Any.TAG: String
    get() = this::class.simpleName.orEmpty()

fun Intent.toKeyValuePair(gson: Gson): Map<String, String> {
    return this.extras?.toKeyValuePair(gson) ?: emptyMap()
}

fun Bundle.toKeyValuePair(gson: Gson): Map<String, String> {
    val bundleMap = mutableMapOf<String, String>()
    keySet()?.forEach { key ->
        get(key)?.let { rawValue ->
            bundleMap[key] = gson.toJson(rawValue)
        }
    }
    return bundleMap
}

fun Long.getDateForTimeStamp(): String {
    return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(this)).toString()
}

fun List<Any>.convertToString(): String {
    return this.joinToString(prefix = "[", separator = ":", postfix = "]")
}
