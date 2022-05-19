package com.freenow.app.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.freenow.app.R
import com.freenow.app.di.AppModule
import com.freenow.app.presentation.theme.FreeNowAppTheme
import com.freenow.app.presentation.vehicle_list.VehicleListMapScreen
import com.freenow.app.presentation.vehicle_list.VehicleListScreen
import com.freenow.app.presentation.vehicle_list.components.NavigationItem
import com.freenow.app.presentation.vehicle_map.VehicleDetailMapScreen
import com.freenow.app.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class VehicleListScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<VehicleActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            FreeNowAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenType.VehicleListScreen.route
                    ) {
                        composable(
                            route = ScreenType.VehicleListScreen.route
                        ) {
                            val navController = rememberNavController()
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            Scaffold(
                                topBar = {
                                    TopAppBar(
                                        title = {
                                            Text(
                                                text = stringResource(R.string.app_name),
                                                fontSize = 18.sp
                                            )
                                        },
                                        backgroundColor = colorResource(id = R.color.purple_500),
                                        contentColor = Color.White
                                    )
                                },
                                bottomBar = {
                                    if (navBackStackEntry?.destination?.route != ScreenType.VehicleDetailScreen.route + "/{vehicle}") {
                                        val items = listOf(
                                            NavigationItem.Home,
                                            NavigationItem.Map
                                        )
                                        BottomNavigation(
                                            backgroundColor = colorResource(id = R.color.purple_500),
                                            contentColor = Color.White
                                        ) {
                                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                                            val currentRoute = navBackStackEntry?.destination?.route
                                            items.forEach { item ->
                                                BottomNavigationItem(
                                                    icon = {
                                                        Icon(
                                                            painterResource(id = item.icon),
                                                            contentDescription = item.title
                                                        )
                                                    },
                                                    label = { Text(text = item.title) },
                                                    selectedContentColor = Color.White,
                                                    unselectedContentColor = Color.White.copy(0.4f),
                                                    alwaysShowLabel = true,
                                                    selected = currentRoute == item.route,
                                                    onClick = {
                                                        navController.navigate(item.route) {
                                                            navController.graph.startDestinationRoute?.let { route ->
                                                                popUpTo(route) {
                                                                    saveState = true
                                                                }
                                                            }
                                                            launchSingleTop = true
                                                            restoreState = true
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            ) { innerPadding ->
                                Box(modifier = Modifier.padding(innerPadding)) {
                                    NavHost(
                                        navController,
                                        startDestination = NavigationItem.Home.route
                                    ) {
                                        composable(NavigationItem.Home.route) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(colorResource(id = R.color.white))
                                                    .wrapContentSize(Alignment.Center)
                                            ) {
                                                VehicleListScreen(navController = navController)
                                            }
                                        }
                                        composable(NavigationItem.Map.route) {
                                            VehicleListMapScreen()
                                        }
                                        composable(route = ScreenType.VehicleDetailScreen.route + "/{vehicle}") {
                                            VehicleDetailMapScreen()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        composeRule.onNodeWithTag(TestTags.VEHICLES_MAP_SECTION).assertDoesNotExist()
        composeRule.onNodeWithTag(TestTags.VEHICLES_SECTION).assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Home").performClick()
    }
}