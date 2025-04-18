package com.example.littlelemoncapstone

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemoncapstone.ui.theme.LittleLemonBlack
import com.example.littlelemoncapstone.ui.theme.LittleLemonGreen
import com.example.littlelemoncapstone.ui.theme.LittleLemonWHite
import com.example.littlelemoncapstone.ui.theme.LittleLemonYellow


@Composable
fun Onboarding(innerPadding: PaddingValues, navController: NavHostController) {
    var firstNameText by remember { mutableStateOf("") }
    var lastNameText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }

    val context: Context = LocalContext.current


    val sharedPrefrences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = ("Logo Image"),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(20.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LittleLemonGreen)
                .height(150.dp)
                .padding(20.dp)
        ) {
            Text(
                text = "Let's get to know you",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = LittleLemonWHite
            )
        }

        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 10.dp)
        ) {
            Text(
                text = "Personal Information",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 30.dp)
            )

            OutlinedTextField(
                value = firstNameText,
                onValueChange = {
                    firstNameText = it
                    sharedPrefrences.edit().putString("firstName", it).apply()
                },
                label = {Text(text = "First Name")},
                modifier = Modifier.padding(bottom = 10.dp)
            )
            OutlinedTextField(
                value = lastNameText,
                onValueChange = {
                    lastNameText = it
                    sharedPrefrences.edit().putString("lastName", it).apply()
                },
                label = {Text(text = "Last Name")},
                modifier = Modifier.padding(bottom = 10.dp)
            )
            OutlinedTextField(
                value = emailText,
                onValueChange = {
                    emailText = it
                    sharedPrefrences.edit().putString("email", it).apply()
                },
                label = {Text(text = "Email")},
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    if(firstNameText.isNotEmpty() && lastNameText.isNotEmpty() && emailText.isNotEmpty()){
                        navController.navigate(Destinations.Home.route)
                    }
                    else{
                        Toast.makeText(context, "Enter the valid details.", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = LittleLemonYellow, contentColor = LittleLemonBlack)
            ) {
                Text(
                    text = "Register"
                )
            }
        }

    }
}

@Preview
@Composable
fun OnboardingPreview(){
    val navController = rememberNavController()
    Onboarding(innerPadding = PaddingValues(), navController = navController)
}