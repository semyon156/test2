package com.example.figma.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.figma.MyApp
import com.example.figma.R
import com.example.figma.domain.model.QueueModel
import com.example.figma.domain.queue.MyQueue
import com.example.figma.presentation.ui.screen.NavigationApp
import com.example.figma.presentation.ui.screen.OnboardingMain
import com.example.figma.presentation.ui.screen.OnboardingScreen
import com.example.figma.presentation.ui.screen.SplashScreen
import com.example.figma.presentation.ui.theme.FigmaTheme
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var supabase: SupabaseClient
    private lateinit var sharedPref: SharedPreferences
    private lateinit var queue: MyQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supabase = MyApp.supabase
        sharedPref = MyApp.sharedPref
        queue = MyApp.queue

        setContent {
            FigmaTheme {
                val navController = rememberNavController()

                StartApp(navController = navController)

                NavigationApp(navController = navController, queue = queue, sharedPref = sharedPref, supabase = supabase)
            }
        }
    }

    @Composable
    private fun StartApp(navController: NavController) {
        LaunchedEffect(Unit) {
            delay(2000)
            val skip = sharedPref.getInt("skip", 2)
            when(skip) {
                2 -> {
                    navController.navigate("onboard")
                }
                1 -> {
                    navController.navigate("onboard")
                    queue.pollItem()
                }
                0 -> {
                    navController.navigate("onboard")
                    queue.pollItem()
                    queue.pollItem()
                }
                -1 -> {
                    queue.clearQueue()
                    navController.navigate("sign_up")
                }
                else -> {
                    navController.navigate("onboard")
                }
            }
        }
    }
}