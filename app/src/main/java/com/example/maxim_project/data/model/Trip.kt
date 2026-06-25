package com.example.maxim_project.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

/**
 * Entitas TRIP (Perjalanan).
 *
 * Menyimpan state perjalanan pengguna saat ini.
 *
 * @param tripId   Primary Key — ID unik perjalanan.
 * @param userId   Foreign Key ke tabel User.
 * @param driverId Foreign Key ke tabel Driver.
 * @param rute     Deskripsi rute singkat (misal: "Lokasi saat ini → Duta Mall").
 * @param tarif    Tarif perjalanan (dalam Rupiah).
 */
@Entity(
    tableName = "trips",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userId"], childColumns = ["userId"]),
        ForeignKey(entity = Driver::class, parentColumns = ["driverId"], childColumns = ["driverId"])
    ]
)
data class Trip(
    @PrimaryKey
    val tripId: String,
    @ColumnInfo(index = true)
    val userId: String,
    @ColumnInfo(index = true)
    val driverId: String,
    val rute: String,
    val tarif: Double,
    val tanggal: String = "",
    val status: String = "ACTIVE"
)
