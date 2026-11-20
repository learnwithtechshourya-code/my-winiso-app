package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.ContactSupportDialog
import com.example.ui.components.IsoDetailDialog
import com.example.ui.components.LegalPolicyDialog
import com.example.ui.components.SignInDialog
import com.example.ui.components.SubscriptionDialog
import com.example.ui.components.WebsitePreviewDialog
import com.example.ui.screens.DownloadsScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.UsbGuideScreen
import com.example.ui.screens.VerifierScreen
import com.example.ui.theme.WinVerifiedGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val context = LocalContext.current

    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val filteredIsos by viewModel.filteredIsos.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val filteredTools by viewModel.filteredTools.collectAsStateWithLifecycle()
    val selectedToolkitCategory by viewModel.selectedToolkitCategory.collectAsStateWithLifecycle()
    val toolkitSearchQuery by viewModel.toolkitSearchQuery.collectAsStateWithLifecycle()
    val bookmarkedIds by viewModel.bookmarkedIds.collectAsStateWithLifecycle()
    val downloadsList by viewModel.downloadsList.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val supportTickets by viewModel.supportTickets.collectAsStateWithLifecycle()

    val showSignInDialog by viewModel.showSignInDialog.collectAsStateWithLifecycle()
    val showSubscriptionDialog by viewModel.showSubscriptionDialog.collectAsStateWithLifecycle()
    val showContactSupportDialog by viewModel.showContactSupportDialog.collectAsStateWithLifecycle()
    val lastSubmittedTicket by viewModel.lastSubmittedTicket.collectAsStateWithLifecycle()
    val legalDialogInitialTab by viewModel.legalDialogInitialTab.collectAsStateWithLifecycle()
    val showWebsiteDialog by viewModel.showWebsiteDialog.collectAsStateWithLifecycle()
    val selectedIso by viewModel.selectedIso.collectAsStateWithLifecycle()
    val selectedArchitecture by viewModel.selectedArchitecture.collectAsStateWithLifecycle()
    val selectedEdition by viewModel.selectedEdition.collectAsStateWithLifecycle()
    val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()

    val inputHash by viewModel.inputHash.collectAsStateWithLifecycle()
    val hashResult by viewModel.hashVerifyResult.collectAsStateWithLifecycle()
    val isHashing by viewModel.isHashingFile.collectAsStateWithLifecycle()
    val hashingProgress by viewModel.hashingProgress.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.openContactSupportDialog() },
                        modifier = Modifier.testTag("top_app_bar_support_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SupportAgent,
                            contentDescription = "Contact Support",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "WinISO Hub",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Official Source",
                            tint = WinVerifiedGreen,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
                actions = {
                    // Quick Pro / Subscription Chip
                    Box(
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (currentUser.isProSubscribed) {
                                    WinVerifiedGreen.copy(alpha = 0.16f)
                                } else {
                                    MaterialTheme.colorScheme.tertiaryContainer
                                }
                            )
                            .clickable { viewModel.openSubscriptionDialog() }
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                            .testTag("top_app_bar_pro_btn"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.WorkspacePremium,
                                contentDescription = "Subscription",
                                tint = if (currentUser.isProSubscribed) {
                                    WinVerifiedGreen
                                } else {
                                    MaterialTheme.colorScheme.onTertiaryContainer
                                },
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = when {
                                    currentUser.subscriptionPlan == com.example.data.model.SubscriptionPlan.PRO_1 -> "PRO 1"
                                    currentUser.isProSubscribed -> "PRO"
                                    else -> "Get Pro"
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (currentUser.isProSubscribed) {
                                    WinVerifiedGreen
                                } else {
                                    MaterialTheme.colorScheme.onTertiaryContainer
                                }
                            )
                        }
                    }

                    // Account / Sign In Pill
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (currentUser.isGuest) MaterialTheme.colorScheme.surfaceVariant
                                else Color(currentUser.provider.badgeColorHex).copy(alpha = 0.15f)
                            )
                            .clickable { viewModel.openSignInDialog() }
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                            .testTag("top_app_bar_account_pill"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(Color(currentUser.provider.badgeColorHex)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = currentUser.avatarInitials,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = if (currentUser.isGuest) "Sign In" else currentUser.displayName.split(" ").first(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (currentUser.isGuest) {
                                    MaterialTheme.colorScheme.onSurface
                                } else {
                                    Color(currentUser.provider.badgeColorHex)
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.testTag("main_navigation_bar"),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                NavigationBarItem(
                    selected = selectedTab == MainNavTab.EXPLORE,
                    onClick = { viewModel.selectTab(MainNavTab.EXPLORE) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == MainNavTab.EXPLORE) Icons.Default.Explore else Icons.Outlined.Explore,
                            contentDescription = "Explore"
                        )
                    },
                    label = { Text("Explore", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_item_explore")
                )

                NavigationBarItem(
                    selected = selectedTab == MainNavTab.VERIFIER,
                    onClick = { viewModel.selectTab(MainNavTab.VERIFIER) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == MainNavTab.VERIFIER) Icons.Default.Security else Icons.Outlined.Security,
                            contentDescription = "Verifier"
                        )
                    },
                    label = { Text("Verifier", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_item_verifier")
                )

                NavigationBarItem(
                    selected = selectedTab == MainNavTab.USB_GUIDE,
                    onClick = { viewModel.selectTab(MainNavTab.USB_GUIDE) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == MainNavTab.USB_GUIDE) Icons.Default.Build else Icons.Outlined.Build,
                            contentDescription = "Tools Kit"
                        )
                    },
                    label = { Text("Tools Kit", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_item_usb_guide")
                )

                NavigationBarItem(
                    selected = selectedTab == MainNavTab.DOWNLOADS,
                    onClick = { viewModel.selectTab(MainNavTab.DOWNLOADS) },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (downloadsList.isNotEmpty()) {
                                    Badge { Text("${downloadsList.size}") }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (selectedTab == MainNavTab.DOWNLOADS) Icons.Default.Download else Icons.Outlined.Download,
                                contentDescription = "Downloads"
                            )
                        }
                    },
                    label = { Text("Library", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_item_downloads")
                )

                NavigationBarItem(
                    selected = selectedTab == MainNavTab.PROFILE,
                    onClick = { viewModel.selectTab(MainNavTab.PROFILE) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == MainNavTab.PROFILE) Icons.Default.Person else Icons.Outlined.Person,
                            contentDescription = "Account"
                        )
                    },
                    label = { Text("Account", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_item_profile")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .widthIn(max = 720.dp)
                    .fillMaxSize()
            ) {
                when (selectedTab) {
                    MainNavTab.EXPLORE -> {
                        ExploreScreen(
                            isos = filteredIsos,
                            selectedCategory = selectedCategory,
                            searchQuery = searchQuery,
                            bookmarkedIds = bookmarkedIds,
                            recentDownloads = downloadsList,
                            isProSubscribed = currentUser.isProSubscribed,
                            onCategorySelect = { viewModel.setCategory(it) },
                            onSearchChange = { viewModel.setSearchQuery(it) },
                            onBookmarkToggle = { viewModel.toggleBookmark(it) },
                            onDownloadClick = { iso -> viewModel.handleIsoDownloadClick(iso) },
                            onReDownload = { item -> viewModel.reDownloadItem(context, item) },
                            onViewAllDownloads = { viewModel.selectTab(MainNavTab.DOWNLOADS) },
                            onDownloadTool = { tool -> viewModel.downloadToolkit(context, tool) },
                            onOpenToolsKit = { viewModel.selectTab(MainNavTab.USB_GUIDE) },
                            onOpenDetails = { iso -> viewModel.openIsoDetail(iso) },
                            onOpenPortal = { url -> viewModel.openOfficialPortal(context, url) },
                            onOpenSubscription = { viewModel.openSubscriptionDialog() }
                        )
                    }

                    MainNavTab.VERIFIER -> {
                        VerifierScreen(
                            inputHash = inputHash,
                            hashResult = hashResult,
                            isHashing = isHashing,
                            hashingProgress = hashingProgress,
                            onHashChange = { viewModel.setInputHash(it) },
                            onVerifyClick = { viewModel.verifyInputHash() },
                            onFileSelect = { uri -> viewModel.verifyFile(context, uri) },
                            onClear = { viewModel.clearHashVerification() }
                        )
                    }

                    MainNavTab.USB_GUIDE -> {
                        UsbGuideScreen(
                            tools = filteredTools,
                            selectedCategory = selectedToolkitCategory,
                            searchQuery = toolkitSearchQuery,
                            onCategorySelect = { viewModel.setToolkitCategory(it) },
                            onSearchChange = { viewModel.setToolkitSearchQuery(it) },
                            onDownloadTool = { tool -> viewModel.downloadToolkit(context, tool) },
                            onShareTool = { tool -> viewModel.shareToolkit(context, tool) },
                            onOpenPortal = { url -> viewModel.openOfficialPortal(context, url) }
                        )
                    }

                    MainNavTab.DOWNLOADS -> {
                        DownloadsScreen(
                            downloads = downloadsList,
                            bookmarkedIds = bookmarkedIds,
                            onReDownload = { item -> viewModel.reDownloadItem(context, item) },
                            onDeleteDownload = { viewModel.deleteDownload(it) },
                            onClearAllDownloads = { viewModel.clearAllDownloads() },
                            onSelectIso = { iso -> viewModel.openIsoDetail(iso) }
                        )
                    }

                    MainNavTab.PROFILE -> {
                        ProfileScreen(
                            user = currentUser,
                            supportTicketCount = supportTickets.size,
                            onOpenSignIn = { viewModel.openSignInDialog() },
                            onSwitchToPro1 = { viewModel.signInWithPro1() },
                            onOpenSubscription = { viewModel.openSubscriptionDialog() },
                            onOpenContactSupport = { viewModel.openContactSupportDialog() },
                            onOpenWebsite = { viewModel.openWebsiteDialog() },
                            onOpenPrivacyPolicy = { viewModel.openPrivacyPolicy() },
                            onOpenCopyright = { viewModel.openCopyrightNotice() },
                            onWifiOnlyChange = { wifiOnly -> viewModel.updateWifiOnlyPreference(wifiOnly) },
                            onSignOut = { viewModel.signOut() }
                        )
                    }
                }
            }
        }
    }

    if (selectedIso != null) {
        IsoDetailDialog(
            iso = selectedIso!!,
            isProSubscribed = currentUser.isProSubscribed,
            selectedArchitecture = selectedArchitecture,
            selectedEdition = selectedEdition,
            selectedLanguage = selectedLanguage,
            onArchitectureSelect = { viewModel.setArchitecture(it) },
            onEditionSelect = { viewModel.setEdition(it) },
            onLanguageSelect = { viewModel.setLanguage(it) },
            onDismiss = { viewModel.closeIsoDetail() },
            onStartDownload = {
                viewModel.startDownload(context)
                viewModel.closeIsoDetail()
            },
            onOpenSubscription = {
                viewModel.closeIsoDetail()
                viewModel.openSubscriptionDialog()
            },
            onCopyLink = { viewModel.copyDownloadLink(context) },
            onShare = { viewModel.shareDownload(context) },
            onOpenPortal = { viewModel.openOfficialPortal(context, selectedIso!!.officialPortalUrl) }
        )
    }

    if (showSignInDialog) {
        SignInDialog(
            onDismiss = { viewModel.closeSignInDialog() },
            onSignInWithGoogle = { name, email -> viewModel.signInWithGoogle(name, email) },
            onSignInWithMicrosoft = { name, email -> viewModel.signInWithMicrosoft(name, email) },
            onSignInWithEmail = { name, email -> viewModel.signInWithEmail(name, email) },
            onSignInWithPro1 = { name, email -> viewModel.signInWithPro1(name, email) },
            onContinueAsGuest = { viewModel.continueAsGuest() }
        )
    }

    if (showSubscriptionDialog) {
        SubscriptionDialog(
            currentPlan = currentUser.subscriptionPlan,
            isSubscribed = currentUser.isProSubscribed,
            userEmail = currentUser.email,
            isGuest = currentUser.isGuest,
            proRequestSent = currentUser.proRequestSent,
            proRequestEmail = currentUser.proRequestEmail,
            proRequestRef = currentUser.proRequestRef,
            onDismiss = { viewModel.closeSubscriptionDialog() },
            onRequestProAccess = { plan, email ->
                viewModel.submitProAccessRequest(plan, email)
            },
            onClearProRequest = { viewModel.clearProAccessRequest() }
        )
    }

    if (showContactSupportDialog) {
        ContactSupportDialog(
            userEmail = currentUser.email,
            isGuest = currentUser.isGuest,
            subscriptionPlan = currentUser.subscriptionPlan,
            recentTickets = supportTickets,
            lastSubmittedTicket = lastSubmittedTicket,
            onDismiss = { viewModel.closeContactSupportDialog() },
            onSubmitTicket = { topic, email, subject, message, diagnostics ->
                viewModel.submitSupportTicket(topic, email, subject, message, diagnostics)
            },
            onClearLastSubmitted = { viewModel.clearLastSubmittedTicket() }
        )
    }

    legalDialogInitialTab?.let { initialTab ->
        LegalPolicyDialog(
            initialTab = initialTab,
            onDismiss = { viewModel.closeLegalDialog() },
            onOpenWebsite = { viewModel.openWebsiteDialog() }
        )
    }

    if (showWebsiteDialog) {
        WebsitePreviewDialog(
            onDismiss = { viewModel.closeWebsiteDialog() }
        )
    }
}
