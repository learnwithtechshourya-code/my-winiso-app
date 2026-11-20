package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WinIsoDao {

    // Downloads
    @Query("SELECT * FROM download_items ORDER BY timestamp DESC")
    fun getAllDownloads(): Flow<List<DownloadEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownload(item: DownloadEntity): Long

    @Update
    suspend fun updateDownload(item: DownloadEntity)

    @Query("DELETE FROM download_items WHERE id = :id")
    suspend fun deleteDownloadById(id: Int)

    @Query("DELETE FROM download_items")
    suspend fun clearAllDownloads()

    // Bookmarks
    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE isoId = :isoId")
    suspend fun deleteBookmark(isoId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE isoId = :isoId)")
    fun isBookmarkedFlow(isoId: String): Flow<Boolean>

    // User Profile
    @Query("SELECT * FROM user_profile WHERE `key` = 'current_user' LIMIT 1")
    fun getUserProfile(): Flow<UserPreferenceEntity?>

    @Query("SELECT * FROM user_profile WHERE `key` = 'current_user' LIMIT 1")
    suspend fun getUserProfileOnce(): UserPreferenceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserPreferenceEntity)

    // Support Tickets
    @Query("SELECT * FROM support_tickets ORDER BY createdAt DESC")
    fun getAllSupportTickets(): Flow<List<SupportTicketEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupportTicket(ticket: SupportTicketEntity): Long
}
