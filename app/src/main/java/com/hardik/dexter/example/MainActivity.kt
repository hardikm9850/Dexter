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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hardik.dexter.example.theme.DexterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DexterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Greeting("Hardik")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        modifier = Modifier.height(20.dp),
        onClick = {
            throw RuntimeException("crash!")
            // val compute = 1 / 0
            // Log.d(MainActivity::class.simpleName, "Computation is $compute")
        },
    ) {
        Text(text = "Click to cause an Arithmetic crash")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DexterTheme {
        Greeting("Android")
    }
}
