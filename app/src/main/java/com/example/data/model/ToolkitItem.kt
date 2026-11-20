package com.example.data.model

enum class ToolkitCategory(val displayName: String) {
    ALL("All Tools"),
    BOOTABLE_USB("Bootable USB"),
    MICROSOFT_OFFICIAL("Microsoft Tools"),
    DIAGNOSTICS("Repair & Utilities")
}

data class ToolkitItem(
    val id: String,
    val name: String,
    val vendor: String,
    val version: String,
    val sizeLabel: String,
    val platform: String,
    val category: ToolkitCategory,
    val summary: String,
    val fileExtension: String,
    val directDownloadUrl: String,
    val officialPortalUrl: String,
    val isRecommended: Boolean = false
)
