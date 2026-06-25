package com.example.maxim_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entitas DRIVER (Pengemudi / Mitra).
 *
 * Menyimpan informasi mitra pengemudi untuk ditampilkan
 * di halaman evaluasi dan tracking.
 *
 * @param driverId       Primary Key — ID unik driver.
 * @param namaDriver     Nama lengkap pengemudi.
 * @param fotoDriver     URL atau path foto profil driver.
 * @param platNomor      Plat nomor kendaraan (e.g., "B 1234 KLM").
 * @param ratingRataRata Rating rata-rata driver (1.0 – 5.0).
 */
@Entity(tableName = "drivers")
data class Driver(
    @PrimaryKey
    val driverId: String,
    val namaDriver: String,
    val fotoDriver: String,
    val platNomor: String,
    val ratingRataRata: Double
)
