package com.poulastaa.nestedtypesafenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.poulastaa.nestedtypesafenavigation.ui.theme.NestedTypeSafeNavigationTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NestedTypeSafeNavigationTheme {
                NestedNavStartDestination()
            }
        }
    }
}

@Serializable
sealed class Screens {
    @Serializable
    object Auth {
        @Serializable
        object EmailSignUp

        @Serializable
        object EmailLogIn
    }

    @Serializable
    object App {
        @Serializable
        data class Home(
            val name: String,
        )
    }
}


@Composable
fun NestedNavStartDestination() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Auth
    ) {
        authGraph(navController)
        appGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(
    navController: NavHostController,
) {
    navigation<Screens.Auth>(
        startDestination = Screens.Auth.EmailLogIn
    ) {
        composable<Screens.Auth.EmailLogIn> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Email LogIn Screen",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.weight(3f))

                Text(
                    text = "Email SingUp",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                        navController.navigate(Screens.Auth.EmailSignUp)
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Auth Success",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                        navController.navigate(
                            Screens.App.Home(
                                name = "Old User"
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.weight(3f))
            }
        }

        composable<Screens.Auth.EmailSignUp> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Email SignUp Screen",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.weight(3f))

                Text(
                    text = "Email LogIn",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                        navController.navigate(Screens.Auth.EmailLogIn)
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Auth Success",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                        navController.navigate(
                            Screens.App.Home(
                                name = "New User"
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.weight(3f))
            }
        }
    }
}

private fun NavGraphBuilder.appGraph(
    navController: NavHostController,
) {
    navigation<Screens.App>(
        startDestination = Screens.App.Home::class
    ) {
        composable<Screens.App.Home> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .padding(56.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val user = it.toRoute<Screens.App.Home>().name

                Spacer(modifier = Modifier.weight(2f))

                Text(
                    text = "Home Screen",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Hello $user",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                        navController.navigate(Screens.Auth.EmailLogIn)
                    }
                )

                Spacer(modifier = Modifier.weight(3f))
            }
        }
    }
}