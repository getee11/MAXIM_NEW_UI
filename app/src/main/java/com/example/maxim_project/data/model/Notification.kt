package com.example.maxim_project.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "notifications",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userId"], childColumns = ["userId"])
    ]
)
data class Notification(
    @PrimaryKey
    val notifId: String,
    @ColumnInfo(index = true)
    val userId: String,
    val iconName: String,
    val title: String,
    val desc: String,
    val time: String,
    val accentHex: String,
    val bgHex: String,
    val isRead: Boolean
)
