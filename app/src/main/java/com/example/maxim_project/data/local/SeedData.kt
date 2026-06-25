package com.example.maxim_project.data.local

import com.example.maxim_project.data.model.*

object SeedData {
    val users = listOf(
        User(userId = "USR-001", nama = "Daffa Ramadhani", saldo = 150_000.0),
        User(userId = "USR-002", nama = "Siti Nurhaliza", saldo = 85_000.0),
        User(userId = "USR-003", nama = "Andi Pratama", saldo = 250_000.0)
    )

    val drivers = listOf(
        Driver(driverId = "DRV-001", namaDriver = "Budi Santoso", fotoDriver = "https://i.pravatar.cc/150?img=11", platNomor = "B 1234 KLM", ratingRataRata = 4.97),
        Driver(driverId = "DRV-002", namaDriver = "Ahmad Suryadi", fotoDriver = "https://i.pravatar.cc/150?img=12", platNomor = "D 5678 XYZ", ratingRataRata = 4.85),
        Driver(driverId = "DRV-003", namaDriver = "Rizki Firmansyah", fotoDriver = "https://i.pravatar.cc/150?img=14", platNomor = "A 9012 BCD", ratingRataRata = 4.72),
        Driver(driverId = "DRV-004", namaDriver = "Slamet Widodo", fotoDriver = "https://i.pravatar.cc/150?img=15", platNomor = "B 3456 EFG", ratingRataRata = 4.60)
    )

    val trips = listOf(
        Trip(tripId = "TRIP-001", userId = "USR-001", driverId = "DRV-001", rute = "Jl. Sudirman → Duta Mall", tarif = 15_000.0),
        Trip(tripId = "TRIP-002", userId = "USR-001", driverId = "DRV-002", rute = "Duta Mall → Bandara Syamsudin Noor", tarif = 65_000.0),
        Trip(tripId = "TRIP-003", userId = "USR-002", driverId = "DRV-003", rute = "Stasiun Gambir → Monas", tarif = 12_000.0),
        Trip(tripId = "TRIP-004", userId = "USR-001", driverId = "DRV-004", rute = "Kampus ULM → Pasar Baru", tarif = 18_000.0)
    )

    val kategoriAduan = listOf(
        KategoriAduan(kategoriId = "KAT-001", namaKategori = "Minta tarif lebih"),
        KategoriAduan(kategoriId = "KAT-002", namaKategori = "Perilaku tidak sopan"),
        KategoriAduan(kategoriId = "KAT-003", namaKategori = "Mengemudi ugal-ugalan"),
        KategoriAduan(kategoriId = "KAT-004", namaKategori = "Kendaraan tidak sesuai"),
        KategoriAduan(kategoriId = "KAT-005", namaKategori = "Membatalkan perjalanan"),
        KategoriAduan(kategoriId = "KAT-006", namaKategori = "Lainnya")
    )

    val seedReports = listOf(
        PostTripReport(reportId = "RPT-SEED-001", tripId = "TRIP-003", driverId = "DRV-003", ratingScore = 2, customReviewText = "Driver mengemudi sangat ugal-ugalan di tikungan, merasa tidak aman.", isEscalatedToCs = true),
        PostTripReport(reportId = "RPT-SEED-002", tripId = "TRIP-004", driverId = "DRV-004", ratingScore = 5, customReviewText = "Perjalanan sangat nyaman, driver ramah dan tepat waktu!", isEscalatedToCs = false)
    )

    val seedCsTickets = listOf(
        CsTicket(tiketId = "TIK-SEED-001", reportId = "RPT-SEED-001", statusLaporan = "Diproses")
    )

    val promos = listOf(
        Promo(promoId = "PRM-001", title = "Diskon 50% Pengguna Baru", desc = "Maks. Rp 10.000", validUntil = "30 Juni 2026", imageResName = "promo_1"),
        Promo(promoId = "PRM-002", title = "Cashback 20% Gopay", desc = "Maks. Rp 5.000", validUntil = "15 Juli 2026", imageResName = "promo_2"),
        Promo(promoId = "PRM-003", title = "Gratis Ongkir Maxim Food", desc = "Minimal pesan Rp 40.000", validUntil = "1 Agustus 2026", imageResName = "promo_3")
    )

    val notifications = listOf(
        Notification(notifId = "NTF-001", userId = "USR-001", iconName = "LocalOffer", title = "Promo spesial untukmu!", desc = "Dapatkan 50% diskon perjalanan dengan kode MAXFIRST50.", time = "2 MNT LALU", accentHex = "0xFF6C5A25", bgHex = "0xFFFCF9DF", isRead = false),
        Notification(notifId = "NTF-002", userId = "USR-001", iconName = "DirectionsCar", title = "Driver sedang menuju lokasimu", desc = "Budi Santoso (B 1234 KLM) dalam perjalanan menjemputmu.", time = "15 MNT LALU", accentHex = "0xFF3B82F6", bgHex = "0xFFE0F2FE", isRead = false),
        Notification(notifId = "NTF-003", userId = "USR-001", iconName = "CheckCircle", title = "Perjalanan selesai", desc = "Perjalanan ke Kelapa Gading selesai. Rp 22.500 ditagih.", time = "1 JAM LALU", accentHex = "0xFF22C55E", bgHex = "0xFFDCFCE7", isRead = true),
        Notification(notifId = "NTF-004", userId = "USR-001", iconName = "CardGiftcard", title = "Flash sale food delivery", desc = "Gratis ongkir untuk pemesanan makanan hari ini!", time = "3 JAM LALU", accentHex = "0xFF8C5C4C", bgHex = "0xFFFFEDD5", isRead = true),
        Notification(notifId = "NTF-005", userId = "USR-001", iconName = "Headphones", title = "CS membalas pesanmu", desc = "Laporan #TKT-2821 telah diproses. Driver mendapat peringatan.", time = "KEMARIN", accentHex = "0xFF3B82F6", bgHex = "0xFFE0F2FE", isRead = true)
    )
}
