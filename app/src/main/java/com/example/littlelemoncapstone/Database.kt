package com.example.littlelemoncapstone

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "menu")
data class MenuItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

@Dao
interface MenuItemDao{
    @Insert
    suspend fun insertAll(items: List<MenuItemEntity>)

    @Query("SELECT * FROM menu")
    fun getAllItems(): Flow<List<MenuItemEntity>>

    @Query("SELECT (SELECT COUNT(*) FROM menu) == 0")
    suspend fun isEmpty(): Boolean
}

@Database(entities = [MenuItemEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun menuItemDao(): MenuItemDao
}