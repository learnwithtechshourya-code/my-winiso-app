package com.example.data.repository

import com.example.data.model.ToolkitCategory
import com.example.data.model.ToolkitItem

object ToolkitCatalog {

    val allTools: List<ToolkitItem> = listOf(
        ToolkitItem(
            id = "tool-rufus",
            name = "Rufus Portable",
            vendor = "Pete Batard",
            version = "4.6",
            sizeLabel = "1.5 MB",
            platform = "Windows x64/x86",
            category = ToolkitCategory.BOOTABLE_USB,
            summary = "Create bootable Windows USB drives with optional TPM 2.0, Secure Boot, and online account bypass.",
            fileExtension = "exe",
            directDownloadUrl = "https://github.com/pbatard/rufus/releases/download/v4.6/rufus-4.6p.exe",
            officialPortalUrl = "https://rufus.ie/",
            isRecommended = true
        ),
        ToolkitItem(
            id = "tool-ventoy",
            name = "Ventoy Multi-Boot",
            vendor = "Ventoy Official",
            version = "1.0.99",
            sizeLabel = "15.9 MB",
            platform = "Windows / Linux",
            category = ToolkitCategory.BOOTABLE_USB,
            summary = "Copy multiple Windows, Server, and macOS/Linux ISO files onto a single USB drive without reformatting.",
            fileExtension = "zip",
            directDownloadUrl = "https://github.com/ventoy/Ventoy/releases/download/v1.0.99/ventoy-1.0.99-windows.zip",
            officialPortalUrl = "https://www.ventoy.net/en/download.html",
            isRecommended = true
        ),
        ToolkitItem(
            id = "tool-win11-mct",
            name = "Media Creation Tool",
            vendor = "Microsoft",
            version = "24H2",
            sizeLabel = "9.8 MB",
            platform = "Windows 10/11",
            category = ToolkitCategory.MICROSOFT_OFFICIAL,
            summary = "Official Microsoft utility to prepare bootable Windows 11 installation USB drives or upgrade your PC.",
            fileExtension = "exe",
            directDownloadUrl = "https://go.microsoft.com/fwlink/?linkid=2156295",
            officialPortalUrl = "https://www.microsoft.com/software-download/windows11",
            isRecommended = true
        ),
        ToolkitItem(
            id = "tool-pc-health",
            name = "PC Health Check",
            vendor = "Microsoft",
            version = "3.9",
            sizeLabel = "14.0 MB",
            platform = "Windows 10/11",
            category = ToolkitCategory.MICROSOFT_OFFICIAL,
            summary = "Check CPU, RAM, TPM 2.0, and Secure Boot compatibility before installing Windows 11.",
            fileExtension = "msi",
            directDownloadUrl = "https://aka.ms/GetPCHealthCheckApp",
            officialPortalUrl = "https://www.microsoft.com/windows/windows-11#pchealthcheck"
        ),
        ToolkitItem(
            id = "tool-sysinternals",
            name = "Sysinternals Suite",
            vendor = "Microsoft",
            version = "2024.10",
            sizeLabel = "49.0 MB",
            platform = "Windows",
            category = ToolkitCategory.MICROSOFT_OFFICIAL,
            summary = "Official Windows troubleshooting kit including Process Explorer, Autoruns, Disk2vhd, and Procmon.",
            fileExtension = "zip",
            directDownloadUrl = "https://download.sysinternals.com/files/SysinternalsSuite.zip",
            officialPortalUrl = "https://learn.microsoft.com/sysinternals/downloads/sysinternals-suite",
            isRecommended = true
        ),
        ToolkitItem(
            id = "tool-etcher",
            name = "balenaEtcher Portable",
            vendor = "Balena",
            version = "1.19.25",
            sizeLabel = "145 MB",
            platform = "Windows x64",
            category = ToolkitCategory.BOOTABLE_USB,
            summary = "Cross-platform USB and SD card image flasher with automatic write verification.",
            fileExtension = "exe",
            directDownloadUrl = "https://github.com/balena-io/etcher/releases/download/v1.19.25/balenaEtcher-Portable-1.19.25.exe",
            officialPortalUrl = "https://etcher.balena.io/"
        ),
        ToolkitItem(
            id = "tool-7zip",
            name = "7-Zip Extractor",
            vendor = "Igor Pavlov",
            version = "24.08",
            sizeLabel = "1.6 MB",
            platform = "Windows x64",
            category = ToolkitCategory.DIAGNOSTICS,
            summary = "Open, inspect, and extract .iso, .wim, .esd, and .zip packages quickly.",
            fileExtension = "exe",
            directDownloadUrl = "https://www.7-zip.org/a/7z2408-x64.exe",
            officialPortalUrl = "https://www.7-zip.org/download.html"
        ),
        ToolkitItem(
            id = "tool-crystaldisk",
            name = "CrystalDiskInfo",
            vendor = "Crystal Dew World",
            version = "9.4.0",
            sizeLabel = "5.4 MB",
            platform = "Windows",
            category = ToolkitCategory.DIAGNOSTICS,
            summary = "Verify SSD and HDD S.M.A.R.T. health status and temperature before installing an OS.",
            fileExtension = "zip",
            directDownloadUrl = "https://crystalmark.info/en/download/",
            officialPortalUrl = "https://crystalmark.info/en/software/crystaldiskinfo/"
        )
    )

    fun findById(id: String): ToolkitItem? = allTools.find { it.id == id }
}
