package com.example.maxim_project.data.model

/**
 * Entitas POST_TRIP_REPORT (Laporan / Ulasan Pasca-Perjalanan).
 *
 * Ini adalah entitas jembatan yang paling krusial — menyimpan
 * hasil evaluasi penumpang setelah perjalanan selesai.
 *
 * Relasi:
 * - Many-to-One ke [Trip]: setiap laporan merujuk ke 1 perjalanan.
 * - Many-to-One ke [Driver]: setiap laporan merujuk ke 1 pengemudi.
 * - Many-to-One ke [KategoriAduan]: banyak laporan bisa dikategorikan ke 1 kategori aduan.
 * - One-to-One ke [CsTicket]: jika dieskalasi, 1 laporan menghasilkan 1 tiket CS.
 *
 * @param reportId         Primary Key — ID unik laporan (UUID).
 * @param tripId           Foreign Key → [Trip.tripId].
 * @param driverId         Foreign Key → [Driver.driverId].
 * @param kategoriId       Foreign Key → [KategoriAduan.kategoriId] (opsional, terisi jika ada aduan).
 * @param ratingScore      Skor rating dari penumpang (1–5).
 * @param customReviewText Teks review/keluhan bebas dari penumpang.
 * @param isEscalatedToCs  Apakah laporan di-eskalasi ke Customer Support.
 */
data class PostTripReport(
    val reportId: String,
    val tripId: String,
    val driverId: String = "",
    val kategoriId: String? = null,
    val ratingScore: Int,
    val customReviewText: String,
    val isEscalatedToCs: Boolean = false
)
