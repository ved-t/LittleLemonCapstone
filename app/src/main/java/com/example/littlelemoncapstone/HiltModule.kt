package com.example.littlelemoncapstone

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json


@Module
@InstallIn(SingletonComponent::class)
object MenuModule {

    @Provides
    fun provideMenuRepository(
        httpClient: HttpClient,
        menuItemDao: MenuItemDao
    ): MenuRepository{
        return MenuRepository(httpClient, menuItemDao)
    }


    @Provides
    fun provideHttpClient(): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    @Provides
    fun provideMenuItemDao(database: AppDatabase): MenuItemDao{
        return database.menuItemDao()
    }

    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}