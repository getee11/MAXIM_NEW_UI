package com.example.maxim_project.data.model

/**
 * Entitas USER (Penumpang).
 *
 * Menyimpan data profil penumpang yang digunakan di halaman Dashboard.
 *
 * @param userId    Primary Key — ID unik pengguna.
 * @param nama      Nama lengkap penumpang.
 * @param saldo     Saldo dompet digital penumpang (dalam Rupiah).
 */
data class User(
    val userId: String,
    val nama: String,
    val saldo: Double
)
