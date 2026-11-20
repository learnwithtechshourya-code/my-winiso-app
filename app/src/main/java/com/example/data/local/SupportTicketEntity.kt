package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "support_tickets")
data class SupportTicketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val referenceCode: String,
    val topic: String,
    val email: String,
    val subject: String,
    val message: String,
    val diagnosticsSummary: String,
    val status: String = "Submitted",
    val createdAt: Long = System.currentTimeMillis()
)
