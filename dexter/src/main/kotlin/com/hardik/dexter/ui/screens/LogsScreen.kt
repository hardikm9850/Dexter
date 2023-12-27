/*
 * Created by Hardik on 27/12/23, 12:35 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 27/12/23, 12:35 pm
 *
 */

package com.hardik.dexter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hardik.dexter.internal.logger.DexterLogger

@Composable
fun LogsScreen(logs: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp,
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color.White)
                    .wrapContentSize(Alignment.Center),
            ) {
                DexterLogger.d("LogScreen", logs)
                Text(
                    text = logs,
                    color = Color.DarkGray,
                    modifier = Modifier.align(Alignment.Start),
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
