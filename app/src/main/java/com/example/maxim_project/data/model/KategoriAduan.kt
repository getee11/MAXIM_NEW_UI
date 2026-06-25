package com.example.maxim_project.data.model

/**
 * Entitas KATEGORI_ADUAN (Kategori Keluhan).
 *
 * Entitas pendukung untuk mengisi opsi dropdown/checklist keluhan penumpang.
 * Contoh: "Tarif Tidak Sesuai", "Ugal-ugalan", "Perilaku tidak sopan".
 *
 * Relasi:
 * - One-to-Many ke [PostTripReport]: 1 kategori bisa digunakan oleh banyak laporan.
 *
 * @param kategoriId   Primary Key — ID unik kategori.
 * @param namaKategori Nama/label kategori aduan.
 */
data class KategoriAduan(
    val kategoriId: String,
    val namaKategori: String
)
