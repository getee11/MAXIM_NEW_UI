package com.example.maxim_project.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.maxim_project.data.local.dao.DriverDao
import com.example.maxim_project.data.local.dao.ReportDao
import com.example.maxim_project.data.local.dao.TripDao
import com.example.maxim_project.data.local.dao.UserDao
import com.example.maxim_project.data.model.Driver
import com.example.maxim_project.data.model.PostTripReport
import com.example.maxim_project.data.model.Trip
import com.example.maxim_project.data.model.User
import com.example.maxim_project.data.model.Promo
import com.example.maxim_project.data.model.Notification
import com.example.maxim_project.data.local.dao.PromoDao
import com.example.maxim_project.data.local.dao.NotificationDao

@Database(
    entities = [User::class, Driver::class, Trip::class, PostTripReport::class, Promo::class, Notification::class],
    version = 2,
    exportSchema = false
)
abstract class MaximDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun driverDao(): DriverDao
    abstract fun tripDao(): TripDao
    abstract fun reportDao(): ReportDao
    abstract fun promoDao(): PromoDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: MaximDatabase? = null

        fun getDatabase(context: Context): MaximDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MaximDatabase::class.java,
                    "maxim_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
