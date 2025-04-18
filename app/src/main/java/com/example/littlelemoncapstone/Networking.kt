package com.example.littlelemoncapstone

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import javax.inject.Inject

@Serializable
data class MenuItem(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
){
    fun toEntity(): MenuItemEntity{
        return MenuItemEntity(
            id = id,
            title = title,
            description = description,
            price = price,
            image = image,
            category = category
        )
    }
}



@Serializable
data class MenuResponse(
    val menu: List<MenuItem>
)

class MenuRepository @Inject constructor(
    private val httpClient: HttpClient,
    private val menuItemDao: MenuItemDao
) {
    suspend fun fetchMenu(): MenuResponse? {
        return  try {
            val menuResponse: String = httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"){
                contentType(ContentType.Application.Json)
            }.body()
            Log.d("menuResponse", menuResponse)
            Json.decodeFromString(MenuResponse.serializer(), menuResponse)
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun storeMenu(menuItem: List<MenuItem>){
        val entities = menuItem.map { it.toEntity() }
        Log.d("menuResponse", "Saved to the database successfully")
        menuItemDao.insertAll(entities)
    }

    suspend fun isEmpty(): Boolean{
        return menuItemDao.isEmpty()
    }

    fun getMenu(): Flow<List<MenuItemEntity>> = menuItemDao.getAllItems()
}

