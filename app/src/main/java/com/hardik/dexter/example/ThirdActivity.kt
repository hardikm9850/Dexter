/*
 * Created by Hardik on 24/12/23, 8:34 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 24/12/23, 8:34 pm
 *
 */

package com.hardik.dexter.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hardik.dexter.example.theme.DexterTheme

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DexterTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Crash()
                    }
                }
            }
        }
    }
}

@Composable
fun Crash(modifier: Modifier = Modifier) {
    Text(
        text = "Let's crash the app now!",
        modifier = modifier,
    )
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        onClick = {
            throw RuntimeException("crash!")
        },
    ) {
        Text(text = "Crash Me")
    }
}