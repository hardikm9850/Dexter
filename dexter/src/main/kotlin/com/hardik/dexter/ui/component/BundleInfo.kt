/*
 * Created by Hardik on 27/12/23, 12:35 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 27/12/23, 12:35 pm
 *
 */

package com.hardik.dexter.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BundleInfo(bundleData: Map<String, String>) {
    bundleData.forEach {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = "{ Key : ${it.key} , value : ${it.value} }")
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}
