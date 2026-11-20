package com.example.data.repository

import com.example.data.local.BookmarkEntity
import com.example.data.local.DownloadEntity
import com.example.data.local.SupportTicketEntity
import com.example.data.local.WinIsoDao
import com.example.data.model.IsoCategory
import com.example.data.model.ToolkitCategory
import com.example.data.model.ToolkitItem
import com.example.data.model.WindowsIso
import kotlinx.coroutines.flow.Flow

class IsoRepository(private val dao: WinIsoDao) {

    fun getCatalogIsos(): List<WindowsIso> = WindowsIsoCatalog.allIsos

    fun getToolkitItems(): List<ToolkitItem> = ToolkitCatalog.allTools

    fun filterTools(category: ToolkitCategory, searchQuery: String): List<ToolkitItem> {
        return ToolkitCatalog.allTools.filter { tool ->
            val matchesCategory = (category == ToolkitCategory.ALL) || (tool.category == category)
            val matchesQuery = if (searchQuery.isBlank()) {
                true
            } else {
                val q = searchQuery.trim().lowercase()
                tool.name.lowercase().contains(q) ||
                        tool.vendor.lowercase().contains(q) ||
                        tool.summary.lowercase().contains(q) ||
                        tool.category.displayName.lowercase().contains(q)
            }
            matchesCategory && matchesQuery
        }
    }

    fun filterIsos(category: IsoCategory, searchQuery: String): List<WindowsIso> {
        return WindowsIsoCatalog.allIsos.filter { iso ->
            val matchesCategory = (category == IsoCategory.ALL) || (iso.category == category)
            val matchesQuery = if (searchQuery.isBlank()) {
                true
            } else {
                val q = searchQuery.trim().lowercase()
                iso.title.lowercase().contains(q) ||
                        iso.versionTag.lowercase().contains(q) ||
                        iso.buildNumber.lowercase().contains(q) ||
                        iso.supportedArchitectures.any { it.lowercase().contains(q) } ||
                        iso.editions.any { it.lowercase().contains(q) }
            }
            matchesCategory && matchesQuery
        }
    }

    val allDownloads: Flow<List<DownloadEntity>> = dao.getAllDownloads()
    val allBookmarks: Flow<List<BookmarkEntity>> = dao.getAllBookmarks()
    val allSupportTickets: Flow<List<SupportTicketEntity>> = dao.getAllSupportTickets()

    suspend fun toggleBookmark(isoId: String, currentlyBookmarked: Boolean) {
        if (currentlyBookmarked) {
            dao.deleteBookmark(isoId)
        } else {
            dao.insertBookmark(BookmarkEntity(isoId = isoId))
        }
    }

    suspend fun recordDownload(
        iso: WindowsIso,
        edition: String,
        architecture: String,
        language: String,
        downloadUrl: String,
        status: String = "STARTED"
    ): Long {
        val entity = DownloadEntity(
            isoId = iso.id,
            title = iso.title,
            edition = edition,
            architecture = architecture,
            language = language,
            downloadUrl = downloadUrl,
            sha256 = iso.officialSha256,
            status = status,
            fileSizeFormatted = iso.estimatedSize
        )
        return dao.insertDownload(entity)
    }

    suspend fun recordToolkitDownload(
        tool: ToolkitItem,
        downloadUrl: String,
        status: String
    ): Long {
        val entity = DownloadEntity(
            isoId = tool.id,
            title = "${tool.name} v${tool.version}",
            edition = tool.category.displayName,
            architecture = tool.platform,
            language = ".${tool.fileExtension.uppercase()}",
            downloadUrl = downloadUrl,
            sha256 = "Official ${tool.vendor}",
            status = status,
            fileSizeFormatted = tool.sizeLabel
        )
        return dao.insertDownload(entity)
    }

    suspend fun deleteDownload(id: Int) {
        dao.deleteDownloadById(id)
    }

    suspend fun clearAllDownloads() {
        dao.clearAllDownloads()
    }

    suspend fun submitSupportTicket(ticket: SupportTicketEntity): Long {
        return dao.insertSupportTicket(ticket)
    }
}
