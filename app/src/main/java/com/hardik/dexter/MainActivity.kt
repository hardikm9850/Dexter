/*
 * Created by Hardik on 24/12/23, 2:27 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 24/12/23, 2:27 pm
 *
 */

package com.hardik.dexter

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
import com.hardik.dexter.ui.theme.IAnveshakTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IAnveshakTheme {
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
    Button(onClick = {
        throw RuntimeException("crash!")
        // val compute = 1 / 0
        // Log.d(MainActivity::class.simpleName, "Computation is $compute")
    }) {
        Text(text = "Click to cause an Arithmetic crash")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IAnveshakTheme {
        Greeting("Android")
    }
}
