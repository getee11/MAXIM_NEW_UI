package com.example.maxim_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "promos")
data class Promo(
    @PrimaryKey
    val promoId: String,
    val title: String,
    val desc: String,
    val validUntil: String,
    val imageResName: String // e.g. a string we can map to a drawable or color for simplicity
)
