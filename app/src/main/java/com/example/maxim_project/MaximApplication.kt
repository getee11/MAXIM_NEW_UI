package com.example.maxim_project

import android.app.Application
import com.example.maxim_project.data.local.MaximDatabase
import com.example.maxim_project.data.repository.MaximRepository

class MaximApplication : Application() {
    val database by lazy { MaximDatabase.getDatabase(this) }
    val repository by lazy { 
        MaximRepository(
            database.userDao(),
            database.driverDao(),
            database.tripDao(),
            database.reportDao(),
            database.promoDao(),
            database.notificationDao()
        )
    }
}
