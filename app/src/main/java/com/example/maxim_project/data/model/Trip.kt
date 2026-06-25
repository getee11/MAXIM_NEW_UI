package com.example.maxim_project.data.model

/**
 * Entitas TRIP (Perjalanan).
 *
 * Menyimpan state perjalanan pengguna saat ini beserta relasi
 * ke [User] (penumpang) dan [Driver] (pengemudi).
 *
 * Relasi:
 * - Many-to-One ke [User]: banyak trip dipesan oleh 1 penumpang.
 * - Many-to-One ke [Driver]: banyak trip dikerjakan oleh 1 pengemudi.
 *
 * @param tripId   Primary Key — ID unik perjalanan.
 * @param userId   Foreign Key → [User.userId].
 * @param driverId Foreign Key → [Driver.driverId].
 * @param rute     Deskripsi rute perjalanan (e.g., "Jl. Sudirman → Duta Mall").
 * @param tarif    Tarif perjalanan dalam Rupiah.
 */
data class Trip(
    val tripId: String,
    val userId: String,
    val driverId: String,
    val rute: String,
    val tarif: Double
)
