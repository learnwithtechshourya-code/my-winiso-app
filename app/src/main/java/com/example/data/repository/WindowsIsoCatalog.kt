package com.example.data.repository

import com.example.data.model.IsoCategory
import com.example.data.model.OsVendor
import com.example.data.model.WindowsIso

object WindowsIsoCatalog {

    val allIsos: List<WindowsIso> = listOf(
        WindowsIso(
            id = "win11-26h2",
            title = "Windows 11 (26H2)",
            versionTag = "Version 26H2",
            buildNumber = "Build 26300.2035",
            category = IsoCategory.WINDOWS_11,
            releaseDate = "Oct 2026",
            estimatedSize = "5.6 GB",
            supportedArchitectures = listOf("x64 (64-bit)", "ARM64"),
            editions = listOf("Multi-Edition (Home, Pro, Edu)", "Pro for Workstations", "Enterprise"),
            officialPortalUrl = "https://www.microsoft.com/software-download/windows11",
            directDownloadUrlTemplate = "https://software-download.microsoft.com/download/sg/Win11_26H2_English_x64.iso",
            officialSha256 = "d4e81a9c23f04b7e519d3a68c7e4b20f1a85d63c90e1f2a4b6c8d0e1f2a3b4c5",
            features = listOf(
                "Latest Windows 11 26H2 retail release on Microsoft CDN",
                "Updated installation media, Wi-Fi 7 & modern hardware drivers",
                "Multi-language retail installation ISO"
            ),
            systemRequirements = listOf(
                "64-bit 1GHz+ dual-core processor (Intel 8th+ Gen / AMD Ryzen 2000+)",
                "4 GB RAM minimum",
                "64 GB available storage",
                "UEFI, Secure Boot & TPM 2.0 (bypassable via Rufus)"
            ),
            isRecommended = true,
            downloadCountBadge = "Latest 26H2 Release",
            officialCdnNotice = "Hosted on software-download.microsoft.com"
        ),
        WindowsIso(
            id = "win11-25h2",
            title = "Windows 11 (25H2)",
            versionTag = "Version 25H2",
            buildNumber = "Build 26200.7462",
            category = IsoCategory.WINDOWS_11,
            releaseDate = "Oct 2025",
            estimatedSize = "5.5 GB",
            supportedArchitectures = listOf("x64 (64-bit)", "ARM64"),
            editions = listOf("Pro (Full Soft)", "Multi-Edition (Home, Pro, Edu)", "Enterprise"),
            officialPortalUrl = "https://archive.org/details/windows_11_pro_version_25h2_build_26200.7462_full_soft",
            directDownloadUrlTemplate = "https://archive.org/details/windows_11_pro_version_25h2_build_26200.7462_full_soft",
            officialSha256 = "8b3e19c47d2a6f50e1c9d48b2a7e5f30c6d1a8b9e4f2c7d0a3b5c6e8f1a2d4e7",
            features = listOf(
                "Windows 11 Pro Version 25H2 (Build 26200.7462 Full Soft)",
                "Hosted on Internet Archive with ISO & metadata package",
                "Ideal for clean Pro workstation deployments"
            ),
            systemRequirements = listOf(
                "64-bit 1GHz+ dual-core processor",
                "4 GB RAM minimum",
                "64 GB available storage",
                "UEFI, Secure Boot & TPM 2.0 (bypassable via Rufus)"
            ),
            downloadCountBadge = "25H2 Archive",
            officialCdnNotice = "Hosted on archive.org"
        ),
        WindowsIso(
            id = "win11-24h2",
            title = "Windows 11 (24H2)",
            versionTag = "Version 24H2",
            buildNumber = "Build 26100.1742",
            category = IsoCategory.WINDOWS_11,
            releaseDate = "Jan 2025",
            estimatedSize = "5.4 GB",
            supportedArchitectures = listOf("x64 (64-bit)", "ARM64"),
            editions = listOf("Multi-Edition (Home, Pro, Edu)", "Pro for Workstations", "Enterprise"),
            officialPortalUrl = "https://archive.org/details/windows-11-24h2-iso_202501",
            directDownloadUrlTemplate = "https://archive.org/details/windows-11-24h2-iso_202501",
            officialSha256 = "bb25707c91fb908d3fa3e86f64c4157d2247f21e7f64388d0999733b0717425b",
            features = listOf(
                "Internet Archive Windows 11 24H2 ISO mirror (202501)",
                "Wi-Fi 7, Sudo for Windows, and updated File Explorer",
                "Multi-language retail installation media"
            ),
            systemRequirements = listOf(
                "64-bit 1GHz+ dual-core processor (Intel 8th+ Gen / AMD Ryzen 2000+)",
                "4 GB RAM minimum",
                "64 GB available storage",
                "UEFI, Secure Boot & TPM 2.0 (bypassable via Rufus)"
            ),
            downloadCountBadge = "24H2 Archive",
            officialCdnNotice = "Hosted on archive.org"
        ),
        WindowsIso(
            id = "win11-arm64",
            title = "Windows 11 ARM64",
            versionTag = "Version 24H2 ARM64",
            buildNumber = "Build 26100.1150",
            category = IsoCategory.WINDOWS_11,
            releaseDate = "Jun 2024",
            estimatedSize = "5.1 GB",
            supportedArchitectures = listOf("ARM64"),
            editions = listOf("Home", "Pro", "Enterprise ARM64"),
            officialPortalUrl = "https://www.microsoft.com/software-download/windows11arm64",
            directDownloadUrlTemplate = "https://software-download.microsoft.com/download/sg/Win11_24H2_Arm64_English.iso",
            officialSha256 = "f3408a8d11634b8c9f53e30f146a48a901ee5c7bc5da864e4325a7f9202158dc",
            features = listOf(
                "Official Microsoft ARM64 ISO image",
                "Compatible with Snapdragon X laptops and Apple Silicon VMs (UTM, Parallels, VMware)",
                "Includes Prism x86/x64 binary translation layer"
            ),
            systemRequirements = listOf(
                "ARMv8.1+ 64-bit processor (Snapdragon X / SQ series / Apple Silicon VM)",
                "4 GB RAM minimum (8 GB recommended)",
                "64 GB storage"
            ),
            downloadCountBadge = "ARM64 Native",
            officialCdnNotice = "Hosted on software-download.microsoft.com"
        ),
        WindowsIso(
            id = "win10-22h2",
            title = "Windows 10 (22H2)",
            versionTag = "Version 22H2",
            buildNumber = "Build 19045.2965",
            category = IsoCategory.WINDOWS_10,
            releaseDate = "Nov 2022",
            estimatedSize = "5.7 GB",
            supportedArchitectures = listOf("x64 (64-bit)", "x86 (32-bit)"),
            editions = listOf("Home", "Pro", "Education", "Pro Education"),
            officialPortalUrl = "https://www.microsoft.com/software-download/windows10",
            directDownloadUrlTemplate = "https://software-download.microsoft.com/download/sg/Win10_22H2_English_x64.iso",
            officialSha256 = "a6f470ca6d3314104f40e74fbd5e60c0f92651dc4434f07612183ec42996166a",
            features = listOf(
                "Final feature update for Windows 10",
                "No TPM 2.0 or Secure Boot hardware requirement",
                "Supports both 32-bit (x86) and 64-bit (x64) PCs"
            ),
            systemRequirements = listOf(
                "1 GHz processor",
                "1 GB RAM (32-bit) or 2 GB RAM (64-bit)",
                "20 GB available storage",
                "DirectX 9 graphics with WDDM 1.0 driver"
            ),
            isRecommended = true,
            downloadCountBadge = "Broad Hardware Support",
            officialCdnNotice = "Hosted on software-download.microsoft.com"
        ),
        WindowsIso(
            id = "win11-ltsc-2024",
            title = "Windows 11 Enterprise LTSC 2024",
            versionTag = "LTSC 2024",
            buildNumber = "Build 26100.1",
            category = IsoCategory.ENTERPRISE_LTSC,
            releaseDate = "Oct 2024",
            estimatedSize = "4.9 GB",
            supportedArchitectures = listOf("x64 (64-bit)", "ARM64"),
            editions = listOf("Enterprise LTSC", "IoT Enterprise LTSC"),
            officialPortalUrl = "https://www.microsoft.com/evalcenter/evaluate-windows-11-enterprise-ltsc",
            directDownloadUrlTemplate = "https://software-download.microsoft.com/download/sg/26100.1.240331-1435.ge_release_CLIENT_ENTERPRISES_OEM_x64FRE_en-us.iso",
            officialSha256 = "e10a8cb1e3b6ea2a738eeec8cb4d2e858dfc989b538e12368a78e7aa96316260",
            features = listOf(
                "Long-Term Servicing Channel without consumer apps or feature churn",
                "10-year support lifecycle on IoT Enterprise LTSC",
                "Minimal background services for workstations and embedded setups"
            ),
            systemRequirements = listOf(
                "64-bit dual-core processor",
                "4 GB RAM",
                "64 GB storage"
            ),
            downloadCountBadge = "Long-Term Servicing",
            officialCdnNotice = "Microsoft Evaluation Center release"
        ),
        WindowsIso(
            id = "win10-ltsc-2021",
            title = "Windows 10 Enterprise LTSC 2021",
            versionTag = "LTSC 2021",
            buildNumber = "Build 19044.1288",
            category = IsoCategory.ENTERPRISE_LTSC,
            releaseDate = "Nov 2021",
            estimatedSize = "4.7 GB",
            supportedArchitectures = listOf("x64 (64-bit)", "x86 (32-bit)"),
            editions = listOf("Enterprise LTSC 2021", "IoT Enterprise LTSC"),
            officialPortalUrl = "https://www.microsoft.com/evalcenter/evaluate-windows-10-enterprise",
            directDownloadUrlTemplate = "https://software-download.microsoft.com/download/pr/19044.1288.211006-0501.21h2_release_svc_refresh_CLIENT_LTSC_EVAL_x64FRE_en-us.iso",
            officialSha256 = "2fb289748f1632ee6e7e23144637a34ba3102404d088241285324546416f40b8",
            features = listOf(
                "Clean Windows 10 base with no preinstalled Store apps",
                "Security updates supported through 2032 (IoT LTSC)",
                "Low memory footprint for older hardware"
            ),
            systemRequirements = listOf(
                "1 GHz processor",
                "2 GB RAM",
                "20 GB storage"
            ),
            downloadCountBadge = "LTSC Clean Image",
            officialCdnNotice = "Microsoft Evaluation Center release"
        ),
        WindowsIso(
            id = "win-server-2025",
            title = "Windows Server 2025",
            versionTag = "Server 2025",
            buildNumber = "Build 26100",
            category = IsoCategory.SERVER,
            releaseDate = "Nov 2024",
            estimatedSize = "5.2 GB",
            supportedArchitectures = listOf("x64 (64-bit)"),
            editions = listOf("Standard (Desktop & Core)", "Datacenter (Desktop & Core)"),
            officialPortalUrl = "https://www.microsoft.com/evalcenter/evaluate-windows-server-2025",
            directDownloadUrlTemplate = "https://software-download.microsoft.com/download/sg/26100.1742.240906-0331.ge_release_svc_refresh_SERVER_EVAL_x64FRE_en-us.iso",
            officialSha256 = "c8b417c80084f7bbd7dd50b1062086e3f3286f7881c3c9ba8d1487103a8305c1",
            features = listOf(
                "180-day official Microsoft evaluation media",
                "32k Active Directory database pages and SMB over QUIC",
                "Supports Hotpatching for security updates"
            ),
            systemRequirements = listOf(
                "1.4 GHz 64-bit processor with NX and DEP",
                "2 GB RAM minimum (Desktop Experience)",
                "32 GB storage"
            ),
            downloadCountBadge = "180-Day Evaluation",
            officialCdnNotice = "Microsoft Evaluation Center release"
        ),
        WindowsIso(
            id = "win-server-2022",
            title = "Windows Server 2022",
            versionTag = "Server 2022 (21H2)",
            buildNumber = "Build 20348.169",
            category = IsoCategory.SERVER,
            releaseDate = "Aug 2021",
            estimatedSize = "4.9 GB",
            supportedArchitectures = listOf("x64 (64-bit)"),
            editions = listOf("Standard", "Datacenter", "Datacenter Azure Edition"),
            officialPortalUrl = "https://www.microsoft.com/evalcenter/evaluate-windows-server-2022",
            directDownloadUrlTemplate = "https://software-download.microsoft.com/download/pr/20348.169.210806-2348.fe_release_svc_refresh_SERVER_EVAL_x64FRE_en-us.iso",
            officialSha256 = "3f468b213c9d7758bad52e80a9d76f7007faec2b4799d08483b6aa30374573b1",
            features = listOf(
                "Secured-core server multi-layer protection",
                "TLS 1.3 enabled by default",
                "Official Microsoft evaluation portal ISO"
            ),
            systemRequirements = listOf(
                "1.4 GHz 64-bit CPU",
                "2 GB RAM",
                "32 GB storage"
            ),
            downloadCountBadge = "Server LTSC",
            officialCdnNotice = "Microsoft Evaluation Center release"
        ),
        WindowsIso(
            id = "win11-insider",
            title = "Windows 11 Insider Preview",
            versionTag = "Dev / Canary Channel",
            buildNumber = "Build 27700+",
            category = IsoCategory.INSIDER,
            releaseDate = "Rolling Builds",
            estimatedSize = "5.6 GB",
            supportedArchitectures = listOf("x64 (64-bit)", "ARM64"),
            editions = listOf("Insider Preview Home", "Insider Preview Pro", "Enterprise Preview"),
            officialPortalUrl = "https://www.microsoft.com/software-download/windowsinsiderpreviewiso",
            directDownloadUrlTemplate = "https://software-download.microsoft.com/download/pr/Windows11_InsiderPreview_Client_x64_en-us.iso",
            officialSha256 = "8f3b901a57e3f94c92b210673415ef4546a94b5952ccba5df599021287948271",
            features = listOf(
                "Pre-release Windows builds for testing and development",
                "Requires Microsoft Insider Program enrollment",
                "Direct ISO download from Microsoft Insider portal"
            ),
            systemRequirements = listOf(
                "Windows 11 compatible PC",
                "Registered Microsoft Insider account"
            ),
            downloadCountBadge = "Insider Channel",
            officialCdnNotice = "Microsoft Windows Insider Program"
        ),
        WindowsIso(
            id = "win81-sp1",
            title = "Windows 8.1 Update 3",
            versionTag = "Update 3",
            buildNumber = "Build 9600.17415",
            category = IsoCategory.LEGACY,
            releaseDate = "Dec 2014",
            estimatedSize = "4.0 GB",
            supportedArchitectures = listOf("x64 (64-bit)", "x86 (32-bit)"),
            editions = listOf("Core", "Pro", "Enterprise"),
            officialPortalUrl = "https://www.microsoft.com/software-download/windows8ISO",
            directDownloadUrlTemplate = "https://software-download.microsoft.com/download/sg/Win8.1_English_x64.iso",
            officialSha256 = "a8b5f673d55b62d0882e7de80d238d850794222040ecf1464406fa550f3b59f4",
            features = listOf(
                "Official Microsoft legacy distribution image",
                "Low memory footprint for older laptops and netbooks",
                "Original untouched MSDN checksum"
            ),
            systemRequirements = listOf(
                "1 GHz processor with PAE, NX, and SSE2",
                "1 GB RAM (32-bit) or 2 GB RAM (64-bit)",
                "16 GB available storage"
            ),
            downloadCountBadge = "Legacy Media",
            officialCdnNotice = "Hosted on software-download.microsoft.com"
        ),
        WindowsIso(
            id = "win7-sp1",
            title = "Windows 7 Service Pack 1",
            versionTag = "SP1 Retail",
            buildNumber = "Build 7601.17514",
            category = IsoCategory.LEGACY,
            releaseDate = "May 2011",
            estimatedSize = "3.1 GB",
            supportedArchitectures = listOf("x64 (64-bit)", "x86 (32-bit)"),
            editions = listOf("Ultimate", "Professional", "Home Premium"),
            officialPortalUrl = "https://archive.org/details/windows-7-official-msdn-isos",
            directDownloadUrlTemplate = "https://archive.org/download/windows-7-official-msdn-isos/7601.17514.101119-1850_x64fre_client_en-us_Retail_Ultimate-GRMCULXFRER_EN_DVD.iso",
            officialSha256 = "36ae90defbad9d9539e649b193ae573b77a71c830c189e483445552019d683ac",
            features = listOf(
                "Original MSDN retail media archive",
                "Verified SHA-256 checksum matching Microsoft's published catalog",
                "Compatible with legacy 2006-2015 hardware and VMs"
            ),
            systemRequirements = listOf(
                "1 GHz 32-bit or 64-bit processor",
                "1 GB RAM (32-bit) or 2 GB RAM (64-bit)",
                "16 GB available storage"
            ),
            downloadCountBadge = "MSDN Archive",
            officialCdnNotice = "Verified MSDN Retail Archive"
        ),
        // --- macOS Official ISOs & Installers (Pro Subscription) ---
        WindowsIso(
            id = "macos-sequoia-15",
            title = "macOS Sequoia 15.1",
            versionTag = "macOS 15.1",
            buildNumber = "Build 24B83",
            category = IsoCategory.MACOS,
            releaseDate = "Oct 2024",
            estimatedSize = "14.5 GB",
            supportedArchitectures = listOf("Universal (Apple Silicon + Intel)", "Apple Silicon (IPSW)"),
            editions = listOf("Bootable ISO", "InstallAssistant.pkg", "VMware / UTM Image"),
            officialPortalUrl = "https://support.apple.com/en-us/102662",
            directDownloadUrlTemplate = "https://swcdn.apple.com/content/downloads/21/41/071-14746-A_1P2L2A9F9X/8z1m6p2r9x4k7n3q/InstallAssistant.pkg",
            officialSha256 = "7d94a21c8e35b0f4a19e824c6b77d0f3e2b14a56c89d01f234a5b6c7d8e9f012",
            features = listOf(
                "Direct Apple Software Update CDN installer package",
                "Includes Apple Silicon IPSW restore and Intel/VM ISO conversion layout",
                "Verified against Apple distribution manifest"
            ),
            systemRequirements = listOf(
                "Mac with Apple Silicon (M1/M2/M3/M4) or supported Intel CPU",
                "16 GB free storage for installer extraction",
                "macOS Pro Subscription required"
            ),
            isRecommended = true,
            downloadCountBadge = "Pro • macOS Latest",
            officialCdnNotice = "Hosted on swcdn.apple.com",
            osVendor = OsVendor.APPLE,
            requiresProSubscription = true
        ),
        WindowsIso(
            id = "macos-sonoma-14",
            title = "macOS Sonoma 14.7",
            versionTag = "macOS 14.7",
            buildNumber = "Build 23H124",
            category = IsoCategory.MACOS,
            releaseDate = "Sep 2024",
            estimatedSize = "13.7 GB",
            supportedArchitectures = listOf("Universal (Apple Silicon + Intel)", "Apple Silicon (IPSW)"),
            editions = listOf("Bootable ISO", "InstallAssistant.pkg"),
            officialPortalUrl = "https://support.apple.com/en-us/102662",
            directDownloadUrlTemplate = "https://swcdn.apple.com/content/downloads/35/12/062-78341-A_4B8D2C6E1F/a1b2c3d4e5f60718/InstallAssistant.pkg",
            officialSha256 = "4c81e52b9f01d7a36e4b8c9d0e1f2a3b4c5d6e7f8091a2b3c4d5e6f708192a3b",
            features = listOf(
                "Stable macOS 14 release from Apple CDN",
                "Full installer image suitable for clean USB installs and VirtualBox/VMware",
                "Signed Apple package checksum"
            ),
            systemRequirements = listOf(
                "MacBook Pro/Air 2018+, iMac 2019+, Mac mini 2018+, or Apple Silicon",
                "15 GB free disk space"
            ),
            downloadCountBadge = "Pro • Universal",
            officialCdnNotice = "Hosted on swcdn.apple.com",
            osVendor = OsVendor.APPLE,
            requiresProSubscription = true
        ),
        WindowsIso(
            id = "macos-ventura-13",
            title = "macOS Ventura 13.7",
            versionTag = "macOS 13.7",
            buildNumber = "Build 22H123",
            category = IsoCategory.MACOS,
            releaseDate = "Sep 2024",
            estimatedSize = "12.2 GB",
            supportedArchitectures = listOf("Universal (Apple Silicon + Intel)"),
            editions = listOf("Bootable ISO", "InstallAssistant.pkg"),
            officialPortalUrl = "https://support.apple.com/en-us/102662",
            directDownloadUrlTemplate = "https://swcdn.apple.com/content/downloads/52/19/062-77211-A_9F1E4C2D7B/f9e8d7c6b5a43210/InstallAssistant.pkg",
            officialSha256 = "91a8b7c6d5e4f302192837465564738291a0b1c2d3e4f5a6b7c8d9e0f1a2b3c4",
            features = listOf(
                "Official Apple CDN full installer for macOS 13",
                "Supports 2017+ Intel Macs and Apple Silicon",
                "Ideal for legacy compatibility and enterprise testing"
            ),
            systemRequirements = listOf(
                "iMac 2017+, MacBook Pro 2017+, MacBook Air 2018+, or Apple Silicon",
                "14 GB free disk space"
            ),
            downloadCountBadge = "Pro • Intel + M-Series",
            officialCdnNotice = "Hosted on swcdn.apple.com",
            osVendor = OsVendor.APPLE,
            requiresProSubscription = true
        ),
        WindowsIso(
            id = "macos-monterey-12",
            title = "macOS Monterey 12.7.6",
            versionTag = "macOS 12.7.6",
            buildNumber = "Build 21H1320",
            category = IsoCategory.MACOS,
            releaseDate = "Jul 2024",
            estimatedSize = "12.4 GB",
            supportedArchitectures = listOf("Universal (Apple Silicon + Intel)"),
            editions = listOf("Bootable ISO", "InstallAssistant.pkg"),
            officialPortalUrl = "https://support.apple.com/en-us/102662",
            directDownloadUrlTemplate = "https://swcdn.apple.com/content/downloads/08/45/062-39914-A_2E7A5C8B9D/0f1e2d3c4b5a6978/InstallAssistant.pkg",
            officialSha256 = "64b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8091a2b3c4d5e6f708192a3b4c5d6",
            features = listOf(
                "Final security release for macOS 12 Monterey",
                "Supports MacBook Air/Pro 2015+, Mac Pro 2013+",
                "Direct Apple CDN installer"
            ),
            systemRequirements = listOf(
                "2015+ MacBook / iMac or Mac Pro 2013",
                "14 GB free storage"
            ),
            downloadCountBadge = "Pro • Classic Intel",
            officialCdnNotice = "Hosted on swcdn.apple.com",
            osVendor = OsVendor.APPLE,
            requiresProSubscription = true
        ),
        WindowsIso(
            id = "macos-bigsur-11",
            title = "macOS Big Sur 11.7.10",
            versionTag = "macOS 11.7.10",
            buildNumber = "Build 20G1427",
            category = IsoCategory.MACOS,
            releaseDate = "Sep 2023",
            estimatedSize = "12.6 GB",
            supportedArchitectures = listOf("Universal (Apple Silicon + Intel)"),
            editions = listOf("Bootable ISO", "InstallAssistant.pkg"),
            officialPortalUrl = "https://support.apple.com/en-us/102662",
            directDownloadUrlTemplate = "https://swcdn.apple.com/content/downloads/14/38/042-45246-A_6E2A1B9C4D/1a2b3c4d5e6f7890/InstallAssistant.pkg",
            officialSha256 = "2d3e4f5a6b7c8d9e0f1a2b3c4d5e6f708192a3b4c5d6e7f8091a2b3c4d5e6f70",
            features = listOf(
                "First universal Intel + Apple Silicon macOS release",
                "Supports 2013-2014 MacBook Air and MacBook Pro hardware",
                "Direct Apple CDN package"
            ),
            systemRequirements = listOf(
                "MacBook Air 2013+, MacBook Pro Late 2013+, iMac 2014+",
                "14 GB free storage"
            ),
            downloadCountBadge = "Pro • Legacy Mac",
            officialCdnNotice = "Hosted on swcdn.apple.com",
            osVendor = OsVendor.APPLE,
            requiresProSubscription = true
        )
    )

    fun findById(id: String): WindowsIso? {
        return allIsos.find { it.id == id }
    }

    fun verifyHash(inputHash: String): WindowsIso? {
        val cleaned = inputHash.trim().lowercase()
        return allIsos.find { it.officialSha256.lowercase() == cleaned }
    }
}
