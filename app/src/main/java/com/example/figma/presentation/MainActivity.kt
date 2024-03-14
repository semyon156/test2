package com.example.figma.presentation

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
                var clickNext by rememberSaveable { mutableStateOf(true) }
                var clickSkip by rememberSaveable { mutableStateOf(false) }

                var queueItemImage by rememberSaveable { mutableIntStateOf(R.drawable.onboard_1) }
                var queueItemText1 by rememberSaveable { mutableStateOf("") }
                var queueItemText2 by rememberSaveable { mutableStateOf("") }

                LaunchedEffect(Unit) {
                    delay(2000)
                    navController.navigate("onboard")
                }

                LaunchedEffect(clickNext) {
                    if (clickNext) {
                        if (queue.getSize() == 0) navController.navigate("holder") else {
                            val item = queue.pollItem()
                            queueItemImage = item!!.image
                            queueItemText1 = item.text1
                            queueItemText2 = item.text2
                        }
                        clickNext = false
                    }
                }

                LaunchedEffect(clickSkip) {
                    if (clickSkip) {
                        navController.navigate("holder")
                        queue.clearQueue()
                        clickSkip = false
                    }
                }

                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") {
                        SplashScreen()
                    }
                    composable("onboard") {
                        OnboardingScreen(
                            image = queueItemImage,
                            text1 = queueItemText1,
                            text2 = queueItemText2,
                            clickNext = {
                                clickNext = true
                            },
                            clickSkip = {
                                clickSkip = true
                            },
                            clickSignIn = { navController.navigate("holder") },
                            clickSignUp = { navController.navigate("holder") },
                            queueSize = queue.getSize()
                        )
                    }
                    composable("holder") {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background)
                        )
                    }
                }
            }
        }
    }
}