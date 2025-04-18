package com.example.littlelemoncapstone


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.AbsoluteAlignment
import com.example.littlelemoncapstone.ui.theme.LittleLemonGreen
import com.example.littlelemoncapstone.ui.theme.LittleLemonWHite
import com.example.littlelemoncapstone.ui.theme.LittleLemonYellow
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.littlelemoncapstone.ui.theme.LittleLemonBlack

@Composable
fun Home(innerPadding: PaddingValues, navController: NavHostController, networkViewModel: NetworkViewModel = hiltViewModel()) {

    val searchPhrase by networkViewModel.searchQuery.collectAsState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,

            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = ("Logo Image"),
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(100.dp)
                    .padding(20.dp)
            )

            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = ("Profile Image"),
                modifier = Modifier
                    .clickable {
                        navController.navigate(Destinations.Profile.route)
                        Log.d("image", "Clicked on image")
                    }
                    .height(100.dp)
                    .padding(20.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LittleLemonGreen)
                .padding(20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                ) {
                    Text(
                        text = "Little Lemon",
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = LittleLemonYellow
                    )
                    Text(
                        text = "Chicago",
                        fontSize = 24.sp,
                        color = LittleLemonWHite,
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    )
                    Text(
                        text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                        fontSize = 12.sp,
                        color = LittleLemonWHite
                    )
                }

                Image(
                    painter = painterResource(R.drawable.hero_image),
                    contentDescription = "Hero Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(150.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }

            OutlinedTextField(
                value = searchPhrase,
                onValueChange = {
                    networkViewModel.onQueryChange(it)
                },
                label = {Text(text = "Enter search phrase")},
                leadingIcon = { Icon( imageVector = Icons.Default.Search, contentDescription = "") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )
        }

        Column(
            horizontalAlignment = AbsoluteAlignment.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "ORDER FOR DELIVERY!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = LittleLemonWHite,
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 10.dp)
            )
        }

        val categoryList by networkViewModel.categoryList.collectAsState()
        val categoryChoosen by networkViewModel.category.collectAsState()

        LazyRow(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ){

            items(categoryList){ category->
                val buttonColors = if (categoryChoosen == category) {
                    ButtonDefaults.buttonColors(
                        containerColor = LittleLemonBlack,
                        contentColor = LittleLemonWHite
                    )
                } else {
                    ButtonDefaults.buttonColors(
                        containerColor = LittleLemonWHite,
                        contentColor = LittleLemonBlack
                    )
                }

                CategoryButton(
                    category, buttonColors = buttonColors
                )
            }
        }

        val menuItems by networkViewModel.filterOnCategory.collectAsState()
        val isLoading = networkViewModel.isLoading

        if(isLoading){
            CircularProgressIndicator()
        }
        else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                items(menuItems){ item ->
                    MenuItem(item)
                }
            }
        }
    }
}

@Composable
private fun CategoryButton(
    category: String,
    networkViewModel: NetworkViewModel = hiltViewModel(),
    buttonColors: ButtonColors
) {
    val categoryChoosen by networkViewModel.category.collectAsState()

    Button(
        onClick = {
            if (categoryChoosen == category) networkViewModel.onCategoryChange("")
            else networkViewModel.onCategoryChange(category)
        },
        colors = buttonColors,
        modifier = Modifier
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = category.replaceFirstChar { it.uppercase() },
        )
    }
}

@Composable
fun MenuItem(menuItem: MenuItemEntity){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = menuItem.title,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
            ) {
                Text(
                    text = menuItem.description,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )

                Text(
                    text = "$ " + menuItem.price + ".00",
                    fontSize = 20.sp,
                )
            }

            AsyncImage(
                model = menuItem.image,
                contentDescription = "My Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}



@Preview
@Composable
fun HomePreview(){
    val navController = rememberNavController()
    Home(innerPadding = PaddingValues(), navController)
}
