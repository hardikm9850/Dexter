/*
 * Created by Hardik on 24/12/23, 10:53 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 24/12/23, 10:53 pm
 *
 */

package com.hardik.dexter.ui

sealed class BottomNavItem(var title: String, var screenRoute: String) {
    data object Logs : BottomNavItem("Crash Logs", "logs")
    data object Activity : BottomNavItem("Activity Track", "activity_journey")
    data object Fragment : BottomNavItem("Fragment Track", "fragment_journey")
}
