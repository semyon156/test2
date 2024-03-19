package com.example.figma.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.figma.R
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@Composable
fun MyTextField(
    nameField: String,
    textHint: String,
    textValue: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    isError: Boolean = false,
) {
    var passVision by rememberSaveable { mutableStateOf(isPassword) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = nameField,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.size(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .border(
                    1.dp,
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(4.dp)
                )
                .background(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.background
                )
                .padding(start = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = textValue,
                onValueChange = { onValueChange(it) },
                textStyle = TextStyle(
                    color = if (!isError) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 40.dp),
                maxLines = 1,
                singleLine = true,
                visualTransformation = if (!passVision) VisualTransformation.None else PasswordVisualTransformation(
                    '*'
                )
            )
            if (isPassword) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 10.dp), contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        painterResource(id = R.drawable.eye_icon),
                        contentDescription = "eye_icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.clickable {
                            passVision = !passVision
                        }
                    )
                }
            }
            if (textValue == "") {
                Text(
                    text = textHint,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}

@Composable
fun SignupScreen(supabase: SupabaseClient) {
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var emailText by rememberSaveable { mutableStateOf("") }
    var pass by rememberSaveable { mutableStateOf("") }
    var pass2 by rememberSaveable { mutableStateOf("") }
    var passError by rememberSaveable { mutableStateOf(false) }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var checkbox by rememberSaveable { mutableStateOf(false) }
    var checkAll by rememberSaveable { mutableStateOf(false) }
    var checkNetworkError by rememberSaveable { mutableStateOf(false) }
    var clickSignUp by rememberSaveable { mutableStateOf(false) }
    var loadingNetwork by rememberSaveable { mutableStateOf(false) }
    var showAlert by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(name, phone, emailText, pass, pass2, checkbox) {
        checkAll = name != "" && phone != "" && emailText != "" && pass != "" && pass2 != "" && checkbox
    }

    LaunchedEffect(clickSignUp) {
        if (clickSignUp) {
            loadingNetwork = true
            val auth = supabase.auth
            try {
                auth.signInWith(Email) {
                    email = emailText
                    password = pass
                }
                checkNetworkError = false
            } catch (e: Exception) {
                checkNetworkError = true
                showAlert = true
            }
            loadingNetwork = false
            clickSignUp = false
        }
    }

    if (showAlert) {
        NetworkErrorScreen(onDismissRequest = { showAlert = false }, confirmButton = { showAlert = false })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.size(30.dp))
        Text(
            text = "Create an account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.size(5.dp))
        Text(
            text = "Complete the sign up process to get started",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.size(20.dp))
        MyTextField(
            nameField = "Full name",
            textHint = "Ivanov Ivan",
            textValue = name,
            onValueChange = { name = it }
        )
        Spacer(modifier = Modifier.size(20.dp))
        MyTextField(
            nameField = "Phone Number",
            textHint = "+7(999)999-99-99",
            textValue = phone,
            onValueChange = { phone = it }
        )
        Spacer(modifier = Modifier.size(20.dp))
        MyTextField(
            nameField = "Email Address",
            textHint = "***********@mail.com",
            textValue = emailText,
            onValueChange = { emailText = it },
            isError = emailError
        )
        Spacer(modifier = Modifier.size(20.dp))
        MyTextField(
            nameField = "Password",
            textHint = "**********",
            textValue = pass,
            onValueChange = { pass = it },
            isPassword = true,
            isError = passError
        )
        Spacer(modifier = Modifier.size(20.dp))
        MyTextField(
            nameField = "Confirm Password",
            textHint = "**********",
            textValue = pass2,
            onValueChange = { pass2 = it },
            isPassword = true,
            isError = passError
        )
        Spacer(modifier = Modifier.size(30.dp))
        Row(modifier = Modifier.padding(end = 20.dp)) {
            Icon(
                painterResource(id = if (!checkbox) R.drawable.checkbox_false else R.drawable.checkbox_true),
                contentDescription = "checkbox",
                modifier = Modifier.clickable { checkbox = !checkbox },
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.size(20.dp))
            val text =
                AnnotatedString.Builder("By ticking this box, you agree to our Terms and conditions and private policy")
            text.addStyle(
                style = SpanStyle(color = MaterialTheme.colorScheme.tertiary),
                start = 0,
                end = 38
            )
            text.addStyle(
                style = SpanStyle(color = MaterialTheme.colorScheme.onTertiary),
                start = 38,
                end = text.length
            )
            Text(
                text = text.toAnnotatedString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 18.sp
            )
        }
        Spacer(modifier = Modifier.size(80.dp))
        Button(
            onClick = {
                if (checkAll) {
                    emailError = !Regex("[a-z0-9]+@[a-z0-9]+\\.ru").matches(emailText)
                    passError = pass != pass2
                }
                if (!emailError && !passError && checkAll) {
                    clickSignUp = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (checkAll) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(4.69.dp)
        ) {
            Text(
                text = "Sign Up",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Already have an account?",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = "Sign in",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "or sign in using",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(6.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Icon(
                painterResource(id = R.drawable.google_icon),
                contentDescription = "google_icon",
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 40.dp), contentAlignment = Alignment.TopCenter) {
        if (loadingNetwork) {
            CircularProgressIndicator()
        }
    }
}