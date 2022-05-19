package com.freenow.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.freenow.app.R
import com.freenow.app.presentation.theme.FreeNowAppTheme
import com.freenow.app.presentation.vehicle_list.VehicleListMapScreen
import com.freenow.app.presentation.vehicle_list.VehicleListScreen
import com.freenow.app.presentation.vehicle_list.components.NavigationItem
import com.freenow.app.presentation.vehicle_map.VehicleDetailMapScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VehicleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                            MainScreen()
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        Scaffold(
            topBar = { TopBar() },
            bottomBar = {
                if (navBackStackEntry?.destination?.route != ScreenType.VehicleDetailScreen.route + "/{vehicle}") {
                    BottomNavigationBar(navController)
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Navigation(navController)
            }
        }
    }

    @Composable
    fun TopBar() {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
            backgroundColor = colorResource(id = R.color.purple_500),
            contentColor = Color.White
        )
    }

    @Composable
    fun HomeScreen(navController: NavHostController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
                .wrapContentSize(Alignment.Center)
        ) {
            VehicleListScreen(navController = navController)
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
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

    @Composable
    fun Navigation(navController: NavHostController) {
        NavHost(navController, startDestination = NavigationItem.Home.route) {
            composable(NavigationItem.Home.route) {
                HomeScreen(navController)
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
