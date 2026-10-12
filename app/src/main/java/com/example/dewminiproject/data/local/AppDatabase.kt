package com.example.dewminiproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dewminiproject.data.local.dao.OrderDao
import com.example.dewminiproject.data.local.dao.ProductDao
import com.example.dewminiproject.data.local.dao.UserDao
import com.example.dewminiproject.data.local.entity.Order
import com.example.dewminiproject.data.local.entity.Product
import com.example.dewminiproject.data.local.entity.User

@Database(entities = [User::class, Product::class, Order::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "printxpress_database"
                )
                    .fallbackToDestructiveMigration() // Useful for development when schema changes
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
