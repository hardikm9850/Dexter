/*
 * Created by Hardik on 29/12/23, 5:35 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 29/12/23, 5:35 pm
 *
 */

package com.hardik.dexter.example

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

class SecondActivity : ComponentActivity() {
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
                        Greet()
                    }
                }
            }
        }
    }
}

@Composable
fun Greet() {
    val context = LocalContext.current
    Text(
        text = "Hello There, Again!",
    )
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        onClick = {
            startThirdActivity(context)
        },
    ) {
        Text(text = "Let's to go to third activity")
    }
}

fun startThirdActivity(context: Context) {
    Intent(context, ThirdActivity::class.java).apply {
        putExtra("key3", "value1")
        putExtra("key4", true)
        putExtra("key5", 10f)
        putExtra("key6", arrayOf("Android Studio", "Xcode", "VS code", "Vim"))
        putExtra("key7", UserModel())
    }.also {
        context.startActivity(it)
    }
}
