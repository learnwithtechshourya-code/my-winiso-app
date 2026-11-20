package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Usb
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ToolkitCategory
import com.example.data.model.ToolkitItem
import com.example.data.repository.ToolkitCatalog
import com.example.data.util.DownloadHelper
import com.example.ui.components.ToolkitCard
import com.example.ui.theme.WinBluePrimary
import com.example.ui.theme.WinVerifiedGreen

@Composable
fun UsbGuideScreen(
    tools: List<ToolkitItem> = ToolkitCatalog.allTools,
    selectedCategory: ToolkitCategory = ToolkitCategory.ALL,
    searchQuery: String = "",
    onCategorySelect: (ToolkitCategory) -> Unit = {},
    onSearchChange: (String) -> Unit = {},
    onDownloadTool: (ToolkitItem) -> Unit = {},
    onShareTool: (ToolkitItem) -> Unit = {},
    onOpenPortal: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedSection by remember { mutableIntStateOf(0) } // 0: Tools Kit, 1: USB Setup Guide
    var selectedToolTab by remember { mutableIntStateOf(0) } // 0: Rufus, 1: Ventoy, 2: Official MCT

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("usb_guide_screen"),
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Image Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_usb_guide),
                    contentDescription = "Tools Kit and Bootable USB Media",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Black.copy(alpha = 0.25f),
                                    Color.Black.copy(alpha = 0.88f)
                                )
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Build, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Windows Tools Kit & USB Hub",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Download Rufus, Ventoy, Microsoft tools, and follow bootable USB steps",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Primary Mode Switcher: Tools Kit vs USB Guide
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                TabRow(
                    selectedTabIndex = selectedSection,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Tab(
                        selected = selectedSection == 0,
                        onClick = { selectedSection = 0 },
                        text = { Text("Tools Kit (${tools.size})", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.testTag("tab_toolkit_downloads")
                    )
                    Tab(
                        selected = selectedSection == 1,
                        onClick = { selectedSection = 1 },
                        text = { Text("USB Setup Guide", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.testTag("tab_usb_setup_guide")
                    )
                }
            }
        }

        if (selectedSection == 0) {
            // Search input for Tools Kit
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .testTag("toolkit_search_input"),
                    placeholder = { Text("Search Rufus, Ventoy, Sysinternals, 7-Zip...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search tools") },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchChange("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }

            // Category Filter Chips
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ToolkitCategory.entries.forEach { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { onCategorySelect(cat) },
                            label = { Text(cat.displayName, fontSize = 12.sp) },
                            modifier = Modifier.testTag("toolkit_chip_${cat.name.lowercase()}")
                        )
                    }
                }
            }

            if (tools.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Build,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "No matching tools found",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Clear your search or choose 'All Tools' above.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(tools, key = { it.id }) { tool ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        ToolkitCard(
                            tool = tool,
                            onDownloadClick = { onDownloadTool(tool) },
                            onShareClick = { onShareTool(tool) },
                            onOpenPortalClick = {
                                onOpenPortal(tool.officialPortalUrl)
                            }
                        )
                    }
                }
            }
        } else {
            // USB Guide Selector Tabs
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    TabRow(
                        selectedTabIndex = selectedToolTab,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Tab(
                            selected = selectedToolTab == 0,
                            onClick = { selectedToolTab = 0 },
                            text = { Text("Rufus", fontWeight = FontWeight.SemiBold) }
                        )
                        Tab(
                            selected = selectedToolTab == 1,
                            onClick = { selectedToolTab = 1 },
                            text = { Text("Ventoy", fontWeight = FontWeight.SemiBold) }
                        )
                        Tab(
                            selected = selectedToolTab == 2,
                            onClick = { selectedToolTab = 2 },
                            text = { Text("MCT / macOS", fontWeight = FontWeight.SemiBold) }
                        )
                    }
                }
            }

            if (selectedToolTab == 0) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                        ) {
                            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.FlashOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Rufus writes Windows ISOs to USB drives and optionally disables Windows 11 TPM 2.0, Secure Boot, and online account checks.",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        StepCard(number = 1, title = "Connect an 8GB+ USB flash drive", description = "Back up existing files first, as the drive will be formatted.")
                        StepCard(number = 2, title = "Open Rufus on Windows", description = "Download the portable executable from the Tools Kit tab or rufus.ie.")
                        StepCard(number = 3, title = "Select your Windows ISO", description = "Under Boot selection, click SELECT and pick the downloaded .iso file.")
                        StepCard(number = 4, title = "Set Partition Scheme to GPT", description = "Use GPT and UEFI (non CSM) for modern systems, or MBR for legacy BIOS.")
                        StepCard(number = 5, title = "Confirm User Experience options and Start", description = "Toggle any optional hardware requirement bypasses in the prompt, then click START.")

                        OutlinedButton(
                            onClick = { DownloadHelper.openOfficialPortal(context, "https://rufus.ie") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Open rufus.ie")
                        }
                    }
                }
            } else if (selectedToolTab == 1) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f))
                        ) {
                            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = WinVerifiedGreen)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Ventoy lets you store multiple ISOs (Windows 11, Windows 10, Server, Linux) on one USB drive without reformatting.",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        StepCard(number = 1, title = "Install Ventoy onto USB", description = "Download Ventoy from the Tools Kit tab, run Ventoy2Disk, select your USB drive, and click Install.")
                        StepCard(number = 2, title = "Copy .iso files onto the drive", description = "Drag your downloaded ISO files directly into the formatted Ventoy volume.")
                        StepCard(number = 3, title = "Boot from USB", description = "Open your PC boot menu (F12, F11, or Del) and pick the desired ISO from the Ventoy menu.")

                        OutlinedButton(
                            onClick = { DownloadHelper.openOfficialPortal(context, "https://www.ventoy.net") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Open ventoy.net")
                        }
                    }
                }
            } else {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StepCard(number = 1, title = "Windows Media Creation Tool", description = "Microsoft's Windows-only utility formats a flash drive and downloads installation files automatically.")
                        StepCard(number = 2, title = "macOS createinstallmedia (Pro ISOs)", description = "On a Mac, install the downloaded InstallAssistant.pkg, then run sudo /Applications/Install\\ macOS*/Contents/Resources/createinstallmedia --volume /Volumes/MyVolume in Terminal.")
                        StepCard(number = 3, title = "Apple Silicon Recovery", description = "For M1–M4 Macs, IPSW files can also be restored directly via Apple Configurator or Finder.")

                        OutlinedButton(
                            onClick = { DownloadHelper.openOfficialPortal(context, "https://support.apple.com/en-us/101578") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("View Apple Bootable Installer Docs")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StepCard(
    number: Int,
    title: String,
    description: String
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(WinBluePrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "$number", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
