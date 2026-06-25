package com.example.maxim_project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maxim_project.data.model.User
import com.example.maxim_project.data.model.Driver
import com.example.maxim_project.data.model.Trip
import com.example.maxim_project.data.model.PostTripReport
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY userId ASC LIMIT 1")
    fun getUser(): Flow<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)
}

@Dao
interface DriverDao {
    @Query("SELECT * FROM drivers ORDER BY driverId ASC LIMIT 1")
    fun getDriver(): Flow<Driver?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDriver(driver: Driver)
}

@Dao
interface TripDao {
    @Query("SELECT * FROM trips WHERE status = 'ACTIVE' ORDER BY tripId DESC LIMIT 1")
    fun getTrip(): Flow<Trip?>
    
    @Query("SELECT * FROM trips WHERE userId = :userId ORDER BY tanggal DESC")
    fun getTripsByUserId(userId: String): Flow<List<Trip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrip(trip: Trip)
}

@Dao
interface ReportDao {
    @Query("SELECT * FROM reports")
    fun getAllReports(): Flow<List<PostTripReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReport(report: PostTripReport)
}
