package com.example.maxim_project.data.model

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
data class Driver(
    val driverId: String,
    val namaDriver: String,
    val fotoDriver: String,
    val platNomor: String,
    val ratingRataRata: Double
)
