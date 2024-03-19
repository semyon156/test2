package com.example.figma.presentation.ui.screen

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.figma.domain.queue.MyQueue
import io.github.jan.supabase.SupabaseClient
import kotlin.coroutines.CoroutineContext

@Composable
fun NavigationApp(navController: NavHostController, queue: MyQueue, sharedPref: SharedPreferences, supabase: SupabaseClient) {
    NavHost(navController = navController, startDestination = "sign_up") {
        composable("splash") {
            SplashScreen()
        }
        composable("onboard") {
            OnboardingMain(navController = navController, queue = queue, sharedPref = sharedPref)
        }
        composable("sign_up") {
            SignupScreen(supabase = supabase)
        }
    }
}