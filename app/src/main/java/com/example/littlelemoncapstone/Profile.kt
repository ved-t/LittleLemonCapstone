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
fun Profile(innerPadding: PaddingValues, navController: NavHostController) {
    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    var firstNameText by remember { mutableStateOf( sharedPreferences.getString("firstName", "") ?: "") }
    var lastNameText by remember { mutableStateOf(sharedPreferences.getString("lastName", "") ?:"") }
    var emailText by remember { mutableStateOf(sharedPreferences.getString("email", "") ?:"") }

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
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp, horizontal = 10.dp)
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
                    sharedPreferences.edit().putString("firstName", it).apply()
                },
                label = {Text(text = "First Name")},
                readOnly = true,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            OutlinedTextField(
                value = lastNameText,
                onValueChange = {
                    lastNameText = it
                    sharedPreferences.edit().putString("lastName", it).apply()
                },
                label = {Text(text = "Last Name")},
                readOnly = true,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            OutlinedTextField(
                value = emailText,
                onValueChange = {
                    emailText = it
                    sharedPreferences.edit().putString("email", it).apply()
                },
                label = {Text(text = "Email")},
                readOnly = true,
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
                    sharedPreferences.edit().remove("firstName").apply()
                    sharedPreferences.edit().remove("lastName").apply()
                    sharedPreferences.edit().remove("email").apply()

                    navController.navigate(Destinations.Onboarding.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = LittleLemonYellow, contentColor = LittleLemonBlack)
            ) {
                Text(
                    text = "Log Out"
                )
            }
        }

    }
}

@Preview
@Composable
fun ProfilePreview(){
    val navController = rememberNavController()
    Profile( innerPadding = PaddingValues(), navController)
}
