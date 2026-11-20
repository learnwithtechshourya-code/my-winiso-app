package com.example.data.model

enum class AuthProvider(val providerName: String, val badgeColorHex: Long) {
    GOOGLE("Google Account", 0xFF4285F4),
    MICROSOFT("Microsoft Account", 0xFF0078D4),
    EMAIL("Email & Password", 0xFF6366F1),
    PRO_1("Pro 1 Account", 0xFFF59E0B),
    GUEST("Guest Access", 0xFF718096)
}

enum class SubscriptionPlan(
    val displayName: String,
    val priceLabel: String,
    val amountInr: Int,
    val billingCycle: String,
    val badgeText: String? = null
) {
    NONE("Free Plan", "₹0", 0, "Windows ISOs included", null),
    PRO_1("Pro 1", "₹99", 99, "1-month starter pass", "Pro 1"),
    MONTHLY("Pro Monthly", "₹199", 199, "per month", "Monthly"),
    ANNUAL("Pro Annual", "₹1,499", 1499, "per year (₹125/mo)", "Save 37%"),
    LIFETIME("Pro Lifetime", "₹3,499", 3499, "one-time", "Best Value")
}

data class UserAccount(
    val id: String,
    val displayName: String,
    val email: String,
    val provider: AuthProvider,
    val isGuest: Boolean,
    val avatarInitials: String,
    val joinedDate: String,
    val verifiedLicenseAccess: Boolean = true,
    val insiderProgramEnrolled: Boolean = false,
    val preferredArchitecture: String = "x64 (64-bit)",
    val preferredLanguage: String = "English (United States)",
    val downloadOverWifiOnly: Boolean = true,
    val isProSubscribed: Boolean = false,
    val subscriptionPlan: SubscriptionPlan = SubscriptionPlan.NONE,
    val proRequestSent: Boolean = false,
    val proRequestEmail: String = "",
    val proRequestRef: String = ""
)
