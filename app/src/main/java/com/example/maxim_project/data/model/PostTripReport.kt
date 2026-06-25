package com.example.maxim_project.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

/**
 * Entitas POST_TRIP_REPORT (Laporan / Ulasan Pasca-Perjalanan).
 *
 * Entitas POST_TRIP_REPORT (Laporan / Ulasan).
 *
 * Menyimpan hasil ketikan penumpang saat melakukan komplain/evaluasi.
 *
 * @param reportId         Primary Key — ID unik laporan.
 * @param tripId           Foreign Key ke tabel Trip.
 * @param driverId         Foreign Key ke tabel Driver.
 * @param ratingScore      Penilaian bintang (1-5). Opsional jika laporan bukan berupa rating murni.
 * @param customReviewText Isi teks keluhan yang diketik bebas oleh penumpang.
 * @param isEscalatedToCs  Flag penanda apakah laporan ini sudah diteruskan ke Customer Service.
 */
@Entity(
    tableName = "reports",
    foreignKeys = [
        ForeignKey(entity = Trip::class, parentColumns = ["tripId"], childColumns = ["tripId"]),
        ForeignKey(entity = Driver::class, parentColumns = ["driverId"], childColumns = ["driverId"])
    ]
)
data class PostTripReport(
    @PrimaryKey
    val reportId: String,
    @ColumnInfo(index = true)
    val tripId: String,
    @ColumnInfo(index = true)
    val driverId: String,
    val kategoriId: String? = null,
    val ratingScore: Int?,
    val customReviewText: String?,
    val isEscalatedToCs: Boolean = false
)
