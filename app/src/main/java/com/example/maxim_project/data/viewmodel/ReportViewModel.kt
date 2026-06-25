package com.example.maxim_project.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.maxim_project.data.local.SeedData
import com.example.maxim_project.data.model.*
import com.example.maxim_project.data.repository.MaximRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class ReportUiState {
    object Idle : ReportUiState()
    object Loading : ReportUiState()
    object Success : ReportUiState()
    data class Error(val message: String) : ReportUiState()
}

sealed class RatingUiState {
    object Idle : RatingUiState()
    object Loading : RatingUiState()
    object Success : RatingUiState()
    data class Error(val message: String) : RatingUiState()
}

class ReportViewModel(private val repository: MaximRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.initializeDataIfEmpty()
        }
    }

    val kategoriAduan: List<KategoriAduan> = SeedData.kategoriAduan

    val currentUser: StateFlow<User?> = repository.currentUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val currentDriver: StateFlow<Driver?> = repository.currentDriver.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val currentTrip: StateFlow<Trip?> = repository.currentTrip.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val reports: StateFlow<List<PostTripReport>> = repository.allReports.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val allPromos: StateFlow<List<Promo>> = repository.allPromos.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Using flatMapLatest to get notifications for the current user
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val notifications: StateFlow<List<Notification>> = currentUser
        .filterNotNull()
        .flatMapLatest { user -> repository.getNotificationsByUserId(user.userId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val userTrips: StateFlow<List<Trip>> = currentUser
        .filterNotNull()
        .flatMapLatest { user -> repository.getTripsByUserId(user.userId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _csTickets = MutableStateFlow(SeedData.seedCsTickets)
    val csTickets: StateFlow<List<CsTicket>> = _csTickets.asStateFlow()

    private val _uiState = MutableStateFlow<ReportUiState>(ReportUiState.Idle)
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    private val _ratingUiState = MutableStateFlow<RatingUiState>(RatingUiState.Idle)
    val ratingUiState: StateFlow<RatingUiState> = _ratingUiState.asStateFlow()

    // Location State
    private val _pickupLocation = MutableStateFlow("Lokasi saat ini")
    val pickupLocation: StateFlow<String> = _pickupLocation.asStateFlow()

    private val _destinationLocation = MutableStateFlow("")
    val destinationLocation: StateFlow<String> = _destinationLocation.asStateFlow()

    fun updateLocations(pickup: String, destination: String) {
        _pickupLocation.value = pickup
        _destinationLocation.value = destination
    }

    fun markAllNotificationsRead() {
        val user = currentUser.value ?: return
        viewModelScope.launch {
            repository.markAllNotificationsAsRead(user.userId)
        }
    }

    fun markNotificationAsRead(notification: Notification) {
        viewModelScope.launch {
            repository.updateNotification(notification.copy(isRead = true))
        }
    }

    fun registerOrLoginUser(name: String, phone: String) {
        viewModelScope.launch {
            val user = repository.currentUser.firstOrNull()
            if (user != null) {
                if (name.isNotBlank() && name != "Pengguna") {
                    repository.insertUser(user.copy(nama = name))
                }
            } else {
                val newUser = User(
                    userId = "USR-001",
                    nama = if (name.isNotBlank() && name != "Pengguna") name else "Penumpang",
                    saldo = 150000.0
                )
                repository.insertUser(newUser)
            }
        }
    }

    fun topUpWallet(amount: Double) {
        viewModelScope.launch {
            val user = currentUser.value ?: return@launch
            repository.insertUser(user.copy(saldo = user.saldo + amount))
        }
    }

    fun createTrip(tarif: Double) {
        viewModelScope.launch {
            val user = currentUser.value ?: return@launch
            val driver = currentDriver.value ?: SeedData.drivers.first()
            val ruteStr = "${_pickupLocation.value} → ${_destinationLocation.value.ifEmpty { "Tujuan" }}"
            
            val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
            val tanggalStr = dateFormat.format(Date())

            val newTrip = Trip(
                tripId = "TRIP-${UUID.randomUUID().toString().take(8).uppercase()}",
                userId = user.userId,
                driverId = driver.driverId,
                rute = ruteStr,
                tarif = tarif,
                tanggal = tanggalStr,
                status = "ACTIVE"
            )

            // Potong saldo
            if (user.saldo >= tarif) {
                repository.insertUser(user.copy(saldo = user.saldo - tarif))
            }

            repository.insertTrip(newTrip)
        }
    }

    fun completeTrip() {
        viewModelScope.launch {
            val trip = currentTrip.value ?: return@launch
            repository.insertTrip(trip.copy(status = "COMPLETED"))
        }
    }

    fun submitReport(report: PostTripReport) {
        if (_uiState.value is ReportUiState.Loading) return

        viewModelScope.launch {
            _uiState.value = ReportUiState.Loading
            try {
                delay(1500)
                repository.submitReport(report)

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

    fun escalateToCs(reportId: String) {
        viewModelScope.launch {
            val report = reports.value.find { it.reportId == reportId }
            if (report != null) {
                repository.submitReport(report.copy(isEscalatedToCs = true))
                createCsTicket(reportId)
            }
        }
    }

    private fun createCsTicket(reportId: String) {
        val existingTicket = _csTickets.value.find { it.reportId == reportId }
        if (existingTicket != null) return

        val newTicket = CsTicket(
            tiketId = "TIK-${UUID.randomUUID().toString().take(8).uppercase()}",
            reportId = reportId,
            statusLaporan = "Menunggu"
        )

        _csTickets.update { it + newTicket }
    }

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

    fun getReportByTripId(tripId: String): PostTripReport? =
        reports.value.find { it.tripId == tripId }

    fun getReportById(reportId: String): PostTripReport? =
        reports.value.find { it.reportId == reportId }

    fun getCsTicketByReportId(reportId: String): CsTicket? =
        csTickets.value.find { it.reportId == reportId }

    fun getDriverForReport(reportId: String): Driver? {
        val report = getReportById(reportId) ?: return null
        return SeedData.drivers.find { it.driverId == report.driverId }
    }

    fun getKategoriForReport(reportId: String): KategoriAduan? {
        val report = getReportById(reportId) ?: return null
        val kategoriId = report.kategoriId ?: return null
        return kategoriAduan.find { it.kategoriId == kategoriId }
    }

    fun getReportsByUserId(userId: String): List<PostTripReport> {
        val userTripIds = SeedData.trips.filter { it.userId == userId }.map { it.tripId }
        return reports.value.filter { it.tripId in userTripIds }
    }

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

                val reviewText = buildString {
                    if (positiveTags.isNotEmpty()) append("Positif: ${positiveTags.joinToString(", ")}")
                    if (negativeTags.isNotEmpty()) {
                        if (isNotEmpty()) append("\n")
                        append("Masalah: ${negativeTags.joinToString(", ")}")
                    }
                    if (comment.isNotBlank()) {
                        if (isNotEmpty()) append("\n\n")
                        append("Komentar: $comment")
                    }
                }

                val tripId = currentTrip.value?.tripId ?: "TRIP-UNKNOWN"
                val driverId = currentDriver.value?.driverId ?: "DRV-UNKNOWN"

                val report = PostTripReport(
                    reportId = "RTG-${UUID.randomUUID().toString().take(8).uppercase()}",
                    tripId = tripId,
                    driverId = driverId,
                    kategoriId = null,
                    ratingScore = rating,
                    customReviewText = reviewText,
                    isEscalatedToCs = false
                )

                repository.submitReport(report)
                _ratingUiState.value = RatingUiState.Success
            } catch (e: Exception) {
                _ratingUiState.value = RatingUiState.Error(
                    message = e.message ?: "Gagal mengirim rating"
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = ReportUiState.Idle
    }

    fun resetRatingState() {
        _ratingUiState.value = RatingUiState.Idle
    }
}

class ReportViewModelFactory(private val repository: MaximRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReportViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
