/*
 * Created by Hardik on 24/12/23, 2:27 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 24/12/23, 2:27 pm
 *
 */

package com.hardik.dexter.internal.logger

interface ILogger {
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
    fun w(tag: String, message: String, throwable: Throwable? = null)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}
