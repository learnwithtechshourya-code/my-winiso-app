package com.example.data.repository

import com.example.data.local.UserPreferenceEntity
import com.example.data.local.WinIsoDao
import com.example.data.model.AuthProvider
import com.example.data.model.SubscriptionPlan
import com.example.data.model.UserAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepository(private val dao: WinIsoDao) {

    val currentUser: Flow<UserAccount> = dao.getUserProfile().map { entity ->
        if (entity != null) {
            val provider = try {
                AuthProvider.valueOf(entity.provider)
            } catch (_: Exception) {
                AuthProvider.GUEST
            }
            val plan = try {
                SubscriptionPlan.valueOf(entity.subscriptionPlan)
            } catch (_: Exception) {
                SubscriptionPlan.NONE
            }
            val initials = entity.displayName
                .trim()
                .split(" ")
                .filter { it.isNotBlank() }
                .take(2)
                .joinToString("") { it.first().uppercase() }
                .ifBlank { "U" }

            UserAccount(
                id = "user_${entity.email.hashCode()}",
                displayName = entity.displayName,
                email = entity.email,
                provider = provider,
                isGuest = entity.isGuest,
                avatarInitials = initials,
                joinedDate = if (entity.isGuest) "Local Session" else "Signed In",
                preferredArchitecture = entity.preferredArchitecture,
                preferredLanguage = entity.preferredLanguage,
                downloadOverWifiOnly = entity.downloadOverWifiOnly,
                isProSubscribed = entity.isProSubscribed,
                subscriptionPlan = plan,
                proRequestSent = entity.proRequestSent,
                proRequestEmail = entity.proRequestEmail,
                proRequestRef = entity.proRequestRef
            )
        } else {
            UserAccount(
                id = "guest_0",
                displayName = "Guest User",
                email = "Local device session",
                provider = AuthProvider.GUEST,
                isGuest = true,
                avatarInitials = "GU",
                joinedDate = "Local Session",
                preferredArchitecture = "x64 (64-bit)",
                preferredLanguage = "English (United States)",
                downloadOverWifiOnly = true,
                isProSubscribed = false,
                subscriptionPlan = SubscriptionPlan.NONE
            )
        }
    }

    suspend fun signInWithGoogle(displayName: String, email: String) {
        val existing = dao.getUserProfileOnce() ?: UserPreferenceEntity()
        dao.saveUserProfile(
            existing.copy(
                displayName = displayName.ifBlank { "Google User" },
                email = email.ifBlank { "account@gmail.com" },
                provider = AuthProvider.GOOGLE.name,
                isGuest = false
            )
        )
    }

    suspend fun signInWithMicrosoft(displayName: String, email: String) {
        val existing = dao.getUserProfileOnce() ?: UserPreferenceEntity()
        dao.saveUserProfile(
            existing.copy(
                displayName = displayName.ifBlank { "Microsoft User" },
                email = email.ifBlank { "account@outlook.com" },
                provider = AuthProvider.MICROSOFT.name,
                isGuest = false
            )
        )
    }

    suspend fun signInWithEmail(displayName: String, email: String) {
        val existing = dao.getUserProfileOnce() ?: UserPreferenceEntity()
        dao.saveUserProfile(
            existing.copy(
                displayName = displayName.ifBlank { email.substringBefore("@").ifBlank { "Member" } },
                email = email.ifBlank { "user@domain.com" },
                provider = AuthProvider.EMAIL.name,
                isGuest = false
            )
        )
    }

    suspend fun signInWithPro1(
        displayName: String = "Pro 1 Account",
        email: String = "pro1@local"
    ) {
        val existing = dao.getUserProfileOnce() ?: UserPreferenceEntity()
        dao.saveUserProfile(
            existing.copy(
                displayName = displayName.ifBlank { "Pro 1 Account" },
                email = email.ifBlank { "pro1@local" },
                provider = AuthProvider.PRO_1.name,
                isGuest = false,
                isProSubscribed = true,
                subscriptionPlan = SubscriptionPlan.PRO_1.name
            )
        )
    }

    suspend fun continueAsGuest() {
        val existing = dao.getUserProfileOnce() ?: UserPreferenceEntity()
        val wasPro1Profile = existing.provider == AuthProvider.PRO_1.name
        dao.saveUserProfile(
            existing.copy(
                displayName = "Guest User",
                email = "Local device session",
                provider = AuthProvider.GUEST.name,
                isGuest = true,
                isProSubscribed = if (wasPro1Profile) false else existing.isProSubscribed,
                subscriptionPlan = if (wasPro1Profile) SubscriptionPlan.NONE.name else existing.subscriptionPlan
            )
        )
    }

    suspend fun updatePreferences(
        arch: String,
        lang: String,
        wifiOnly: Boolean
    ) {
        val existing = dao.getUserProfileOnce() ?: UserPreferenceEntity()
        dao.saveUserProfile(
            existing.copy(
                preferredArchitecture = arch,
                preferredLanguage = lang,
                downloadOverWifiOnly = wifiOnly
            )
        )
    }

    suspend fun submitProAccessRequest(
        plan: SubscriptionPlan,
        email: String,
        refCode: String
    ) {
        val existing = dao.getUserProfileOnce() ?: UserPreferenceEntity()
        dao.saveUserProfile(
            existing.copy(
                subscriptionPlan = plan.name,
                proRequestSent = true,
                proRequestEmail = email,
                proRequestRef = refCode
            )
        )
    }

    suspend fun clearProAccessRequest() {
        val existing = dao.getUserProfileOnce() ?: UserPreferenceEntity()
        dao.saveUserProfile(
            existing.copy(
                isProSubscribed = false,
                subscriptionPlan = SubscriptionPlan.NONE.name,
                proRequestSent = false,
                proRequestEmail = "",
                proRequestRef = ""
            )
        )
    }

    suspend fun signOut() {
        continueAsGuest()
    }
}
