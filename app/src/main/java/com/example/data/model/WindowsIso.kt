package com.example.data.model

enum class OsVendor(val vendorName: String, val badgeLabel: String) {
    MICROSOFT("Microsoft", "Official Microsoft CDN"),
    APPLE("Apple", "Official Apple CDN")
}

data class WindowsIso(
    val id: String,
    val title: String,
    val versionTag: String,
    val buildNumber: String,
    val category: IsoCategory,
    val releaseDate: String,
    val estimatedSize: String,
    val supportedArchitectures: List<String>,
    val editions: List<String>,
    val officialPortalUrl: String,
    val directDownloadUrlTemplate: String,
    val officialSha256: String,
    val features: List<String>,
    val systemRequirements: List<String>,
    val isRecommended: Boolean = false,
    val downloadCountBadge: String = "Official Release",
    val officialCdnNotice: String = "Direct official CDN download.",
    val osVendor: OsVendor = OsVendor.MICROSOFT,
    val requiresProSubscription: Boolean = false
)
