package com.example.data.model

enum class IsoCategory(val displayName: String) {
    ALL("All ISOs"),
    WINDOWS_11("Windows 11"),
    WINDOWS_10("Windows 10"),
    SERVER("Windows Server"),
    ENTERPRISE_LTSC("Enterprise & LTSC"),
    LEGACY("Legacy (7 / 8.1)"),
    INSIDER("Insider Preview"),
    MACOS("macOS (Pro)")
}
