package com.dimrnhhh.moneytopia

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dimrnhhh.moneytopia.components.navigation.BottomNavItem
import com.dimrnhhh.moneytopia.pages.AboutPage
import com.dimrnhhh.moneytopia.pages.AddExpensePage
import com.dimrnhhh.moneytopia.pages.AnalyticsPage
import com.dimrnhhh.moneytopia.pages.ExpensesPage
import com.dimrnhhh.moneytopia.pages.LanguagesPage
import com.dimrnhhh.moneytopia.pages.ReportsPage
import com.dimrnhhh.moneytopia.pages.SettingsPage
import com.dimrnhhh.moneytopia.ui.theme.MoneytopiaTheme
import com.dimrnhhh.moneytopia.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isReady.value
            }
        }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MoneytopiaTheme {
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                val itemsBottomBar = listOf(
                    BottomNavItem(
                        title = stringResource(R.string.expenses_title),
                        selectedIcon = Icons.Filled.AccountBalanceWallet,
                        unselectedIcon = Icons.Outlined.AccountBalanceWallet,
                        route = "expenses"
                    ),
                    BottomNavItem(
                        title = stringResource(R.string.reports_title),
                        selectedIcon = Icons.Filled.Description,
                        unselectedIcon = Icons.Outlined.Description,
                        route = "reports"
                    ),
                    BottomNavItem(
                        title = stringResource(R.string.analytics_title),
                        selectedIcon = Icons.Filled.Analytics,
                        unselectedIcon = Icons.Outlined.Analytics,
                        route = "analytics"
                    ),
                    BottomNavItem(
                        title = stringResource(R.string.settings_title),
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings,
                        route = "settings"
                    ),
                )
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
                val fabState = rememberSaveable { mutableStateOf(true) }
                when(backStackEntry?.destination?.route) {
                    "expenses" -> {
                        bottomBarState.value = true
                        fabState.value = true
                    }
                    "expenses/add_expenses" -> {
                        bottomBarState.value = false
                        fabState.value = false
                    }
                    "reports" -> {
                        bottomBarState.value = true
                        fabState.value = false
                    }
                    "analytics" -> {
                        bottomBarState.value = true
                        fabState.value = false
                    }
                    "settings" -> {
                        bottomBarState.value = true
                        fabState.value = false
                    }
                    "settings/languages" -> {
                        bottomBarState.value = false
                        fabState.value = false
                    }
                    "settings/about" -> {
                        bottomBarState.value = false
                        fabState.value = false
                    }
                }
                Scaffold(
                    floatingActionButton = {
                        AnimatedVisibility(
                            visible = fabState.value,
                            enter = fadeIn(animationSpec = tween(durationMillis = 1)),
                            exit = fadeOut(animationSpec = tween(durationMillis = 1))
                        ) {
                            FloatingActionButton(
                                onClick = { navController.navigate("expenses/add_expenses") },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    bottomBar = {
                        AnimatedVisibility(
                            visible = bottomBarState.value,
                            enter = slideInVertically(initialOffsetY = { it }),
                            exit = slideOutVertically(targetOffsetY = { it })
                        ) {
                            NavigationBar {
                                itemsBottomBar.forEachIndexed { index, it ->
                                    NavigationBarItem(
                                        selected = backStackEntry?.destination?.route == it.route,
                                        onClick = {
                                            selectedItemIndex = index
                                            navController.navigate(it.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = false
                                                    if(it.route == "expenses") {
                                                        inclusive = true
                                                    }
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        label = {
                                            Text(
                                                text = it.title,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if(backStackEntry?.destination?.route == it.route) {
                                                    it.selectedIcon
                                                } else it.unselectedIcon,
                                                contentDescription = it.title
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "expenses",
                        enterTransition = {
                            if(initialState.destination.route == backStackEntry?.destination?.route) {
                                fadeIn(animationSpec = tween(durationMillis = 1))
                            } else {
                                fadeIn(animationSpec = tween(durationMillis = 500))
                            }
                        },
                        exitTransition = {
                            if(initialState.destination.route == backStackEntry?.destination?.route) {
                                fadeOut(animationSpec = tween(durationMillis = 1))
                            } else {
                                fadeOut(animationSpec = tween(durationMillis = 500))
                            }
                        }
                    ) {
                        composable("expenses") {
                            ExpensesPage(navController)
                        }
                        composable("expenses/add_expenses") {
                            AddExpensePage(navController)
                        }
                        composable("reports") {
                            ReportsPage(navController)
                        }
                        composable("analytics") {
                            AnalyticsPage(navController)
                        }
                        composable("settings") {
                            SettingsPage(navController)
                        }
                        composable("settings/languages") {
                            LanguagesPage(navController)
                        }
                        composable("settings/about") {
                            AboutPage(navController)
                        }
                    }
                }
            }
        }
    }
}