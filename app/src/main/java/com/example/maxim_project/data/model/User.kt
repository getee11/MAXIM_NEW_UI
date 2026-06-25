package com.example.maxim_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entitas USER (Penumpang).
 *
 * Menyimpan data profil penumpang yang digunakan di halaman Dashboard.
 *
 * @param userId    Primary Key — ID unik pengguna.
 * @param nama      Nama lengkap penumpang.
 * @param saldo     Saldo dompet digital penumpang (dalam Rupiah).
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val userId: String,
    val nama: String,
    val saldo: Double
)
