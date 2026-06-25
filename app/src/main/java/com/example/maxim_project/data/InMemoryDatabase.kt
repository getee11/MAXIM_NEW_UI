package com.example.maxim_project.data

import com.example.maxim_project.data.model.*

/**
 * Simulasi database lokal In-Memory.
 *
 * Object singleton ini bertindak sebagai "server database" yang menyimpan
 * seluruh data dummy untuk keperluan simulasi tanpa backend/Firebase.
 *
 * Skema relasi:
 * ```
 * USER ──┐                              KATEGORI_ADUAN
 *        │ (M:1)                              │ (M:1)
 *        ▼                                    ▼
 *      TRIP ──── (1:1) ──── POST_TRIP_REPORT
 *        ▲                       │
 *        │ (M:1)                 │ (1:1, opsional)
 * DRIVER ─┘                      ▼
 *                            CS_TICKET
 * ```
 */
object InMemoryDatabase {

    // ═══════════════════════════════════════════════════════════════════
    //  TABEL 1: USER (Penumpang)
    // ═══════════════════════════════════════════════════════════════════

    val users = listOf(
        User(
            userId = "USR-001",
            nama = "Daffa Ramadhani",
            saldo = 150_000.0
        ),
        User(
            userId = "USR-002",
            nama = "Siti Nurhaliza",
            saldo = 85_000.0
        ),
        User(
            userId = "USR-003",
            nama = "Andi Pratama",
            saldo = 250_000.0
        )
    )

    /** Pengguna aktif saat ini (default: user pertama). */
    val currentUser: User = users.first()

    // ═══════════════════════════════════════════════════════════════════
    //  TABEL 2: DRIVER (Pengemudi)
    // ═══════════════════════════════════════════════════════════════════

    val drivers = listOf(
        Driver(
            driverId = "DRV-001",
            namaDriver = "Budi Santoso",
            fotoDriver = "https://i.pravatar.cc/150?img=11",
            platNomor = "B 1234 KLM",
            ratingRataRata = 4.97
        ),
        Driver(
            driverId = "DRV-002",
            namaDriver = "Ahmad Suryadi",
            fotoDriver = "https://i.pravatar.cc/150?img=12",
            platNomor = "D 5678 XYZ",
            ratingRataRata = 4.85
        ),
        Driver(
            driverId = "DRV-003",
            namaDriver = "Rizki Firmansyah",
            fotoDriver = "https://i.pravatar.cc/150?img=14",
            platNomor = "A 9012 BCD",
            ratingRataRata = 4.72
        ),
        Driver(
            driverId = "DRV-004",
            namaDriver = "Slamet Widodo",
            fotoDriver = "https://i.pravatar.cc/150?img=15",
            platNomor = "B 3456 EFG",
            ratingRataRata = 4.60
        )
    )

    /** Driver aktif untuk trip saat ini (default: driver pertama). */
    val currentDriver: Driver = drivers.first()

    // ═══════════════════════════════════════════════════════════════════
    //  TABEL 3: TRIP (Perjalanan)
    // ═══════════════════════════════════════════════════════════════════

    val trips = listOf(
        Trip(
            tripId = "TRIP-001",
            userId = "USR-001",
            driverId = "DRV-001",
            rute = "Jl. Sudirman → Duta Mall",
            tarif = 15_000.0
        ),
        Trip(
            tripId = "TRIP-002",
            userId = "USR-001",
            driverId = "DRV-002",
            rute = "Duta Mall → Bandara Syamsudin Noor",
            tarif = 65_000.0
        ),
        Trip(
            tripId = "TRIP-003",
            userId = "USR-002",
            driverId = "DRV-003",
            rute = "Stasiun Gambir → Monas",
            tarif = 12_000.0
        ),
        Trip(
            tripId = "TRIP-004",
            userId = "USR-001",
            driverId = "DRV-004",
            rute = "Kampus ULM → Pasar Baru",
            tarif = 18_000.0
        )
    )

    /** Trip aktif saat ini (default: trip pertama — simulasi trip yang sedang/baru selesai). */
    val currentTrip: Trip = trips.first()

    // ═══════════════════════════════════════════════════════════════════
    //  TABEL 4: KATEGORI_ADUAN
    // ═══════════════════════════════════════════════════════════════════

    val kategoriAduan = listOf(
        KategoriAduan(kategoriId = "KAT-001", namaKategori = "Minta tarif lebih"),
        KategoriAduan(kategoriId = "KAT-002", namaKategori = "Perilaku tidak sopan"),
        KategoriAduan(kategoriId = "KAT-003", namaKategori = "Mengemudi ugal-ugalan"),
        KategoriAduan(kategoriId = "KAT-004", namaKategori = "Kendaraan tidak sesuai"),
        KategoriAduan(kategoriId = "KAT-005", namaKategori = "Membatalkan perjalanan"),
        KategoriAduan(kategoriId = "KAT-006", namaKategori = "Lainnya")
    )

    // ═══════════════════════════════════════════════════════════════════
    //  TABEL 5: POST_TRIP_REPORT (Laporan / Ulasan)
    //  → Dikelola oleh ReportViewModel secara reaktif via StateFlow
    // ═══════════════════════════════════════════════════════════════════

    /** Seed data — contoh laporan historis untuk demo. */
    val seedReports = listOf(
        PostTripReport(
            reportId = "RPT-SEED-001",
            tripId = "TRIP-003",
            driverId = "DRV-003",
            kategoriId = "KAT-003",
            ratingScore = 2,
            customReviewText = "Driver mengemudi sangat ugal-ugalan di tikungan, merasa tidak aman.",
            isEscalatedToCs = true
        ),
        PostTripReport(
            reportId = "RPT-SEED-002",
            tripId = "TRIP-004",
            driverId = "DRV-004",
            kategoriId = null,
            ratingScore = 5,
            customReviewText = "Perjalanan sangat nyaman, driver ramah dan tepat waktu!",
            isEscalatedToCs = false
        )
    )

    // ═══════════════════════════════════════════════════════════════════
    //  TABEL 6: CS (Customer Service Ticket)
    //  → Dikelola oleh ReportViewModel saat eskalasi
    // ═══════════════════════════════════════════════════════════════════

    /** Seed data — contoh tiket CS historis. */
    val seedCsTickets = listOf(
        CsTicket(
            tiketId = "TIK-SEED-001",
            reportId = "RPT-SEED-001",
            statusLaporan = "Diproses"
        )
    )

    // ═══════════════════════════════════════════════════════════════════
    //  HELPER: Relasi Lookup
    // ═══════════════════════════════════════════════════════════════════

    /** Cari driver berdasarkan ID. */
    fun getDriverById(driverId: String): Driver? =
        drivers.find { it.driverId == driverId }

    /** Cari user berdasarkan ID. */
    fun getUserById(userId: String): User? =
        users.find { it.userId == userId }

    /** Cari trip berdasarkan ID. */
    fun getTripById(tripId: String): Trip? =
        trips.find { it.tripId == tripId }

    /** Cari kategori aduan berdasarkan ID. */
    fun getKategoriById(kategoriId: String): KategoriAduan? =
        kategoriAduan.find { it.kategoriId == kategoriId }

    /** Cari semua trip milik user tertentu. */
    fun getTripsByUserId(userId: String): List<Trip> =
        trips.filter { it.userId == userId }

    /** Cari driver yang mengerjakan trip tertentu. */
    fun getDriverForTrip(tripId: String): Driver? {
        val trip = getTripById(tripId) ?: return null
        return getDriverById(trip.driverId)
    }
}
