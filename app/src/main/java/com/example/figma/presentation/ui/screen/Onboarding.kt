package com.example.figma.presentation.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.figma.R
import com.example.figma.presentation.ui.theme.FigmaTheme

@Composable
fun OnboardingScreen(
    image: Int,
    text1: String,
    text2: String,
    clickNext: () -> Unit,
    clickSkip: () -> Unit,
    clickSignIn: () -> Unit,
    clickSignUp: () -> Unit,
    queueSize: Int
) {
    var isHolder by rememberSaveable { mutableStateOf(false) }

    if (!isHolder) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(top = 40.dp, start = 20.dp, end = 20.dp)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "onboard_image",
                modifier = Modifier.fillMaxWidth()
            )
            Column(modifier = Modifier.padding(top = 380.dp)) {
                Text(
                    text = text1,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
                Text(
                    text = text2,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 560.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (queueSize != 0) {
                        Button(
                            onClick = {
                                clickSkip()
                                isHolder = true
                            },
                            modifier = Modifier.size(100.dp, 50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
                            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(4.69.dp)
                        ) {
                            Text(
                                text = "Skip",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Button(
                        onClick = {
                            if (queueSize != 0) clickNext() else clickSignUp()
                            if (queueSize == 0) {
                                isHolder = true
                            }
                        },
                        modifier = if (queueSize != 0) Modifier
                            .size(
                                100.dp,
                                50.dp
                            )
                            .testTag("next") else Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("next"),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(4.69.dp)
                    ) {
                        Text(
                            text = if (queueSize != 0) "Next" else "Sign Up",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
                if (queueSize == 0) {
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Already have an account?",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.tertiary,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Sign in",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.clickable {
                                isHolder = true
                            }
                        )
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .testTag("holder")
        )
    }
}