package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "download_items")
data class DownloadEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isoId: String,
    val title: String,
    val edition: String,
    val architecture: String,
    val language: String,
    val downloadUrl: String,
    val sha256: String,
    val status: String, // "COMPLETED", "IN_PROGRESS", "QUEUED", "LINK_COPIED", "EXTERNAL"
    val timestamp: Long = System.currentTimeMillis(),
    val localFilePath: String? = null,
    val fileSizeFormatted: String = "~5.4 GB"
)
