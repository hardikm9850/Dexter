/*
 * Created by Hardik on 30/12/23, 1:33 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 30/12/23, 1:33 pm
 *
 */

package com.hardik.dexter.example.screen

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.hardik.dexter.ui.themes.DexterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DexterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Greeting()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val context = LocalContext.current
    Text(
        text = "Hello There!",
    )
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        onClick = {
            startSecondActivity(context)
        },
    ) {
        Text(text = "Click to go to second activity")
    }
}

fun startSecondActivity(context: Context) {
    val platforms = ArrayList<String>()
    platforms.add("Android")
    platforms.add("iOS")
    platforms.add("Flutter")
    Intent(context, SecondActivity::class.java).apply {
        putExtra("key1", "value1")
        putExtra("key2", true)
        putExtra("key3", platforms)
    }.also {
        context.startActivity(it)
    }
}
