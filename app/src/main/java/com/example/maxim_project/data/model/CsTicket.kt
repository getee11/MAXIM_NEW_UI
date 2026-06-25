package com.example.maxim_project.data.model

/**
 * Entitas CS (Customer Service Ticket).
 *
 * Tabel referensi untuk menangani eskalasi laporan ke Customer Support.
 * Dibuat otomatis saat [PostTripReport.isEscalatedToCs] bernilai true.
 *
 * Relasi:
 * - One-to-One ke [PostTripReport]: 1 laporan yang dieskalasi menghasilkan 1 tiket CS.
 *
 * @param tiketId       Primary Key — ID unik tiket CS.
 * @param reportId      Foreign Key → [PostTripReport.reportId].
 * @param statusLaporan Status penanganan tiket ("Menunggu", "Diproses", "Selesai").
 */
data class CsTicket(
    val tiketId: String,
    val reportId: String,
    val statusLaporan: String = "Menunggu"
)
