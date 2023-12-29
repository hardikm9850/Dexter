/*
 * Created by Hardik on 29/12/23, 5:47 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 29/12/23, 5:47 pm
 *
 */

package com.hardik.dexter.utils.ext

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Intent.toKeyValuePair(): Map<String, String> {
    return this.extras?.toKeyValuePair() ?: emptyMap()
}

fun Bundle.toKeyValuePair(): Map<String, String> {
    val bundleMap = mutableMapOf<String, String>()
    keySet()?.forEach { key ->
        get(key)?.let { value ->
            bundleMap[key] = when (value) {
                is Array<*> -> this.getStringArray(key)?.toList()
                    ?.convertToString().orEmpty()
                is Parcelable -> this.getParcelable<Parcelable>(key).toString()
                is Serializable -> this.getSerializable(key).toString()
                else -> this.get(key).toString()
            }
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
