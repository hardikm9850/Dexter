/*
 * Created by Hardik on 27/12/23, 12:35 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 27/12/23, 12:35 pm
 *
 */

package com.hardik.dexter.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hardik.dexter.internal.model.ActivityInfo
import com.hardik.dexter.ui.component.BundleInfo

@Composable
fun ActivityScreen(activityJourney: List<ActivityInfo>) {
    val collapsedState =
        remember(activityJourney) { activityJourney.map { true }.toMutableStateList() }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
    ) {
        activityJourney.forEachIndexed { index, activityInfo ->
            val collapsed = collapsedState[index]
            item(key = "$index") {
                if (index != 0) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Card(
                    shape = RoundedCornerShape(size = 4.dp),
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier.clickable {
                        collapsedState[index] = !collapsed
                    },
                    elevation = 4.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .wrapContentHeight()
                                .padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "Name\n${activityInfo.activity}")
                            Text(text = "Created at\n${activityInfo.createdTimeStamp}")
                        }
                        if (!collapsed && activityInfo.bundleData.isNotEmpty()) {
                            Spacer(modifier = Modifier.padding(vertical = 2.dp))
                            Text(text = "Bundle info")
                            BundleInfo(activityInfo.bundleData)
                            Spacer(modifier = Modifier.padding(vertical = 2.dp))
                        }
                    }
                }
            }
        }
    }
}
