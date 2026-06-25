package com.example.maxim_project.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maxim_project.data.InMemoryDatabase
import com.example.maxim_project.data.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * Sealed class merepresentasikan state pengiriman laporan.
 * Mengikuti pola UiState agar UI bisa react terhadap setiap tahap proses.
 */
sealed class ReportUiState {
    /** Belum ada aksi — form kosong / siap diisi. */
    object Idle : ReportUiState()

    /** Sedang mengirim laporan (simulasi network call). */
    object Loading : ReportUiState()

    /** Laporan berhasil dikirim. */
    object Success : ReportUiState()

    /** Gagal mengirim laporan. */
    data class Error(val message: String) : ReportUiState()
}

/**
 * Sealed class merepresentasikan state pengiriman rating.
 * Terpisah dari [ReportUiState] agar flow rating dan aduan tidak saling mengganggu.
 */
sealed class RatingUiState {
    object Idle : RatingUiState()
    object Loading : RatingUiState()
    object Success : RatingUiState()
    data class Error(val message: String) : RatingUiState()
}

/**
 * ViewModel untuk seluruh modul pelaporan & evaluasi penumpang.
 *
 * Mengelola 3 tabel yang bersifat mutable secara reaktif:
 * - [PostTripReport] — Laporan/ulasan (Tabel 5)
 * - [CsTicket] — Tiket Customer Support (Tabel 6)
 *
 * Tabel statis (User, Driver, Trip, KategoriAduan) dibaca langsung
 * dari [InMemoryDatabase] singleton.
 *
 * Pola MVVM:
 * - UI hanya membaca StateFlow (reports, csTickets, uiState, dll)
 * - Aksi pengguna diteruskan via public functions
 * - Tidak ada logika bisnis di layer UI
 */
class ReportViewModel : ViewModel() {

    // ═══════════════════════════════════════════════════════════════════
    //  READ-ONLY: Data Statis dari InMemoryDatabase
    // ═══════════════════════════════════════════════════════════════════

    /** Daftar semua kategori aduan (untuk dropdown/checklist di UI). */
    val kategoriAduan: List<KategoriAduan> = InMemoryDatabase.kategoriAduan

    /** Data driver aktif saat ini. */
    val currentDriver: Driver = InMemoryDatabase.currentDriver

    /** Data trip aktif saat ini. */
    val currentTrip: Trip = InMemoryDatabase.currentTrip

    /** Data user/penumpang aktif saat ini. */
    val currentUser: User = InMemoryDatabase.currentUser

    // ═══════════════════════════════════════════════════════════════════
    //  TABEL 5: POST_TRIP_REPORT — Reactive StateFlow
    // ═══════════════════════════════════════════════════════════════════

    /** Daftar seluruh laporan (seed + user-submitted). */
    private val _reports = MutableStateFlow(InMemoryDatabase.seedReports)
    val reports: StateFlow<List<PostTripReport>> = _reports.asStateFlow()

    // ═══════════════════════════════════════════════════════════════════
    //  TABEL 6: CS_TICKET — Reactive StateFlow
    // ═══════════════════════════════════════════════════════════════════

    /** Daftar seluruh tiket CS (seed + auto-generated). */
    private val _csTickets = MutableStateFlow(InMemoryDatabase.seedCsTickets)
    val csTickets: StateFlow<List<CsTicket>> = _csTickets.asStateFlow()

    // ═══════════════════════════════════════════════════════════════════
    //  UI STATE
    // ═══════════════════════════════════════════════════════════════════

    /** State proses pengiriman laporan aduan saat ini. */
    private val _uiState = MutableStateFlow<ReportUiState>(ReportUiState.Idle)
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    /** State proses pengiriman rating (terpisah dari aduan). */
    private val _ratingUiState = MutableStateFlow<RatingUiState>(RatingUiState.Idle)
    val ratingUiState: StateFlow<RatingUiState> = _ratingUiState.asStateFlow()

    // ═══════════════════════════════════════════════════════════════════
    //  ACTION: Submit Report (Tabel 5)
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Mengirim laporan baru.
     *
     * Flow:
     * 1. Set state → Loading
     * 2. Simulasi delay 1500ms (network call)
     * 3. Insert ke _reports (in-memory)
     * 4. Jika [PostTripReport.isEscalatedToCs] = true, auto-create [CsTicket]
     * 5. Set state → Success
     *
     * @param report Data laporan yang akan dikirim.
     */
    fun submitReport(report: PostTripReport) {
        // Cegah duplikat submit saat masih loading
        if (_uiState.value is ReportUiState.Loading) return

        viewModelScope.launch {
            _uiState.value = ReportUiState.Loading

            try {
                // Simulasi network call ke server
                delay(1500)

                // INSERT ke tabel POST_TRIP_REPORT
                _reports.update { currentList ->
                    currentList + report
                }

                // Jika langsung di-eskalasi, auto-create tiket CS
                if (report.isEscalatedToCs) {
                    createCsTicket(report.reportId)
                }

                _uiState.value = ReportUiState.Success
            } catch (e: Exception) {
                _uiState.value = ReportUiState.Error(
                    message = e.message ?: "Terjadi kesalahan saat mengirim laporan"
                )
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  ACTION: Eskalasi ke CS (Tabel 5 + Tabel 6)
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Eskalasi laporan ke Customer Support.
     *
     * 1. Update flag [PostTripReport.isEscalatedToCs] → true
     * 2. Auto-create [CsTicket] dengan status "Menunggu"
     *
     * Relasi: POST_TRIP_REPORT (1:1) → CS_TICKET
     *
     * @param reportId ID laporan yang akan dieskalasi.
     */
    fun escalateToCs(reportId: String) {
        // Update flag di tabel POST_TRIP_REPORT
        _reports.update { currentList ->
            currentList.map { report ->
                if (report.reportId == reportId) {
                    report.copy(isEscalatedToCs = true)
                } else {
                    report
                }
            }
        }

        // Auto-create tiket CS (relasi 1:1)
        createCsTicket(reportId)
    }

    // ═══════════════════════════════════════════════════════════════════
    //  ACTION: Manage CS Tickets (Tabel 6)
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Buat tiket CS baru untuk laporan yang dieskalasi.
     * Cek duplikat — jangan buat tiket jika sudah ada untuk reportId ini.
     *
     * @param reportId FK → [PostTripReport.reportId]
     */
    private fun createCsTicket(reportId: String) {
        // Cek apakah tiket sudah ada (relasi 1:1)
        val existingTicket = _csTickets.value.find { it.reportId == reportId }
        if (existingTicket != null) return

        val newTicket = CsTicket(
            tiketId = "TIK-${UUID.randomUUID().toString().take(8).uppercase()}",
            reportId = reportId,
            statusLaporan = "Menunggu"
        )

        _csTickets.update { currentList ->
            currentList + newTicket
        }
    }

    /**
     * Update status tiket CS.
     *
     * @param tiketId ID tiket yang akan diupdate.
     * @param newStatus Status baru ("Menunggu", "Diproses", "Selesai").
     */
    fun updateCsTicketStatus(tiketId: String, newStatus: String) {
        _csTickets.update { currentList ->
            currentList.map { ticket ->
                if (ticket.tiketId == tiketId) {
                    ticket.copy(statusLaporan = newStatus)
                } else {
                    ticket
                }
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  QUERY: Lookup / Join Helpers
    // ═══════════════════════════════════════════════════════════════════

    /** Cari laporan berdasarkan trip ID (relasi TRIP → POST_TRIP_REPORT). */
    fun getReportByTripId(tripId: String): PostTripReport? =
        _reports.value.find { it.tripId == tripId }

    /** Cari laporan berdasarkan report ID. */
    fun getReportById(reportId: String): PostTripReport? =
        _reports.value.find { it.reportId == reportId }

    /** Cari tiket CS berdasarkan report ID (relasi POST_TRIP_REPORT → CS_TICKET). */
    fun getCsTicketByReportId(reportId: String): CsTicket? =
        _csTickets.value.find { it.reportId == reportId }

    /** Cari driver untuk laporan tertentu (join via driverId). */
    fun getDriverForReport(reportId: String): Driver? {
        val report = getReportById(reportId) ?: return null
        return InMemoryDatabase.getDriverById(report.driverId)
    }

    /** Cari kategori aduan untuk laporan tertentu (join via kategoriId). */
    fun getKategoriForReport(reportId: String): KategoriAduan? {
        val report = getReportById(reportId) ?: return null
        val kategoriId = report.kategoriId ?: return null
        return InMemoryDatabase.getKategoriById(kategoriId)
    }

    /** Dapatkan semua laporan milik user tertentu (join: REPORT → TRIP → USER). */
    fun getReportsByUserId(userId: String): List<PostTripReport> {
        val userTripIds = InMemoryDatabase.getTripsByUserId(userId).map { it.tripId }
        return _reports.value.filter { it.tripId in userTripIds }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  ACTION: Submit Rating (Tabel 5 — via rating flow)
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Mengirim rating/ulasan pasca-perjalanan.
     *
     * Berbeda dari [submitReport] yang untuk aduan, ini khusus
     * untuk rating positif/netral setelah trip selesai.
     *
     * @param rating      Skor rating (1-5).
     * @param comment     Komentar opsional dari penumpang.
     * @param positiveTags Tag positif yang dipilih.
     * @param negativeTags Tag negatif yang dipilih.
     */
    fun submitRating(
        rating: Int,
        comment: String = "",
        positiveTags: List<String> = emptyList(),
        negativeTags: List<String> = emptyList()
    ) {
        if (_ratingUiState.value is RatingUiState.Loading) return

        viewModelScope.launch {
            _ratingUiState.value = RatingUiState.Loading

            try {
                delay(1500)

                // Build review text dari tags + comment
                val reviewText = buildString {
                    if (positiveTags.isNotEmpty()) {
                        append("Positif: ${positiveTags.joinToString(", ")}")
                    }
                    if (negativeTags.isNotEmpty()) {
                        if (isNotEmpty()) append("\n")
                        append("Masalah: ${negativeTags.joinToString(", ")}")
                    }
                    if (comment.isNotBlank()) {
                        if (isNotEmpty()) append("\n\n")
                        append("Komentar: $comment")
                    }
                }

                val report = PostTripReport(
                    reportId = "RTG-${UUID.randomUUID().toString().take(8).uppercase()}",
                    tripId = currentTrip.tripId,
                    driverId = currentDriver.driverId,
                    kategoriId = null, // Rating bukan aduan, tidak perlu kategori
                    ratingScore = rating,
                    customReviewText = reviewText,
                    isEscalatedToCs = false
                )

                _reports.update { it + report }
                _ratingUiState.value = RatingUiState.Success
            } catch (e: Exception) {
                _ratingUiState.value = RatingUiState.Error(
                    message = e.message ?: "Gagal mengirim rating"
                )
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  STATE MANAGEMENT
    // ═══════════════════════════════════════════════════════════════════

    /** Reset report aduan UI state ke Idle. */
    fun resetState() {
        _uiState.value = ReportUiState.Idle
    }

    /** Reset rating UI state ke Idle. */
    fun resetRatingState() {
        _ratingUiState.value = RatingUiState.Idle
    }
}
