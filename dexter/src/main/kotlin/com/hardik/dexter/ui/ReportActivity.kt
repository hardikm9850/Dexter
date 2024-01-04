/*
 * Created by Hardik on 01/01/24, 7:03 pm
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 01/01/24, 7:03 pm
 *
 */

package com.hardik.dexter.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hardik.dexter.internal.model.ActivityInfo
import com.hardik.dexter.internal.model.FragmentInfo
import com.hardik.dexter.ui.screens.ActivityScreen
import com.hardik.dexter.ui.screens.FragmentScreen
import com.hardik.dexter.ui.screens.LogsScreen
import com.hardik.dexter.ui.themes.DexterTheme

class ReportActivity : androidx.activity.ComponentActivity() {

    private val logs by lazy {
        intent.getStringExtra(FLAG_CRASH) ?: ""
    }

    private val activityJourney by lazy {
        intent.getParcelableArrayListExtra<ActivityInfo>(FLAG_ACTIVITY_JOURNEY)
    }

    private val fragmentJourney by lazy {
        intent.getParcelableArrayListExtra<FragmentInfo>(FLAG_FRAGMENT_JOURNEY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReportView()
        }
    }

    private fun shareCrashReport() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, prepareCrashReport())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "")
        ContextCompat.startActivity(this, shareIntent, null)
    }

    private fun prepareCrashReport() = buildString {
        append("===== Stack Trace ====")
        append(logs)
        append("\n\n===== Activity journey ======")
        append(activityJourney.toString())
        append("\n\n===== Fragment journey ======")
        append(fragmentJourney.toString())
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ReportView() {
        val navController = rememberNavController()
        DexterTheme {
            Scaffold(
                topBar = {
                    TopAppBar(
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        title = {
                            Text(text = "Dexter", style = MaterialTheme.typography.titleLarge)
                        },
                        actions = {
                            IconButton(onClick = {
                                shareCrashReport()
                            }) {
                                Icon(Icons.Filled.Share, "Send logs", tint = Color.White)
                            }
                        },
                    )
                },
                bottomBar = {
                    SetupBottomNavigationMenu(navController)
                },
            ) {
                Box(modifier = Modifier.padding(it)) {
                    NavigationGraph(navController)
                }
            }
        }
    }

    @Composable
    private fun SetupBottomNavigationMenu(navController: NavHostController) {
        val options = listOf(BottomNavItem.Logs, BottomNavItem.Activity, BottomNavItem.Fragment)
        var selectedScreen by remember { mutableStateOf(options.first()) }

        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.primary,
        ) {
            options.forEach { screen ->
                BottomNavigationItem(
                    icon = {},
                    label = { Text(screen.title, style = MaterialTheme.typography.labelSmall) },
                    selected = screen.title == selectedScreen.title,
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        selectedScreen = screen
                        navController.navigate(screen.screenRoute) {
                            navController.graph.startDestinationRoute?.let { screenRoute ->
                                popUpTo(screenRoute) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        }
    }

    @Composable
    fun NavigationGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = BottomNavItem.Logs.screenRoute) {
            composable(BottomNavItem.Logs.screenRoute) {
                LogsScreen(logs)
            }
            composable(BottomNavItem.Activity.screenRoute) {
                ActivityScreen(activityJourney.orEmpty())
            }
            composable(BottomNavItem.Fragment.screenRoute) {
                FragmentScreen(fragmentJourney.orEmpty())
            }
        }
    }

    companion object {

        fun showForensicReport(
            context: Context,
            stackTrace: String,
            activityJourney: List<ActivityInfo>,
            fragmentJourney: List<FragmentInfo>,
        ) {
            Intent(context, ReportActivity::class.java).apply {
                putExtra(FLAG_CRASH, stackTrace)
                activityJourney.takeIf { it.isNotEmpty() }?.run {
                    putParcelableArrayListExtra(FLAG_ACTIVITY_JOURNEY, ArrayList(this))
                }
                fragmentJourney.takeIf { it.isNotEmpty() }?.run {
                    putParcelableArrayListExtra(FLAG_FRAGMENT_JOURNEY, ArrayList(this))
                }
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }.also { intent ->
                context.startActivity(intent)
            }
        }

        private const val FLAG_CRASH = "BUNDLE_FLAG_CRASH"
        private const val FLAG_ACTIVITY_JOURNEY = "BUNDLE_ACTIVITY_JOURNEY"
        private const val FLAG_FRAGMENT_JOURNEY = "BUNDLE_FRAGMENT_JOURNEY"
    }
}
