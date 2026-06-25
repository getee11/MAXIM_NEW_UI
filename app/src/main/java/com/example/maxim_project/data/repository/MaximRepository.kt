package com.example.maxim_project.data.repository

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import com.example.maxim_project.data.local.SeedData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MaximRepository(
    private val userDao: UserDao,
    private val driverDao: DriverDao,
    private val tripDao: TripDao,
    private val reportDao: ReportDao,
    private val promoDao: PromoDao,
    private val notificationDao: NotificationDao
) {
    suspend fun initializeDataIfEmpty() {
        withContext(Dispatchers.IO) {
            if (userDao.getUser().firstOrNull() == null) {
                SeedData.users.forEach { userDao.insertUser(it) }
                SeedData.drivers.forEach { driverDao.insertDriver(it) }
                SeedData.trips.forEach { tripDao.insertTrip(it) }
                SeedData.seedReports.forEach { reportDao.insertReport(it) }
                SeedData.promos.forEach { promoDao.insertPromo(it) }
                SeedData.notifications.forEach { notificationDao.insertNotification(it) }
            }
        }
    }

    val currentUser: Flow<User?> = userDao.getUser()
    val currentDriver: Flow<Driver?> = driverDao.getDriver()
    val currentTrip: Flow<Trip?> = tripDao.getTrip()
    val allReports: Flow<List<PostTripReport>> = reportDao.getAllReports()
    val allPromos: Flow<List<Promo>> = promoDao.getAllPromos()
    
    fun getTripsByUserId(userId: String): Flow<List<Trip>> = tripDao.getTripsByUserId(userId)
    fun getNotificationsByUserId(userId: String): Flow<List<Notification>> = notificationDao.getNotificationsByUserId(userId)
    
    suspend fun markAllNotificationsAsRead(userId: String) {
        withContext(Dispatchers.IO) {
            notificationDao.markAllAsRead(userId)
        }
    }

    suspend fun updateNotification(notification: Notification) {
        withContext(Dispatchers.IO) {
            notificationDao.updateNotification(notification)
        }
    }

    suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    suspend fun insertDriver(driver: Driver) {
        withContext(Dispatchers.IO) {
            driverDao.insertDriver(driver)
        }
    }

    suspend fun insertTrip(trip: Trip) {
        withContext(Dispatchers.IO) {
            tripDao.insertTrip(trip)
        }
    }

    suspend fun submitReport(report: PostTripReport) {
        withContext(Dispatchers.IO) {
            reportDao.insertReport(report)
        }
    }
}
