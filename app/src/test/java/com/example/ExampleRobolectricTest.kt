package com.example

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.DownloadEntity
import com.example.data.model.AuthProvider
import com.example.data.model.IsoCategory
import com.example.data.model.OsVendor
import com.example.data.model.SubscriptionPlan
import com.example.data.model.ToolkitCategory
import com.example.data.model.UserAccount
import com.example.data.repository.ToolkitCatalog
import com.example.data.repository.WindowsIsoCatalog
import com.example.ui.components.ContactSupportDialog
import com.example.ui.components.LegalPolicyDialog
import com.example.ui.components.SignInDialog
import com.example.ui.components.SubscriptionDialog
import com.example.ui.components.VersionSelectorPanel
import com.example.ui.screens.DownloadsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.UsbGuideScreen
import com.example.ui.theme.MyApplicationTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class ExampleRobolectricTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("WinISO Hub", appName)
  }

  @Test
  fun `verify catalog includes windows and pro macos isos`() {
    val isos = WindowsIsoCatalog.allIsos
    assertTrue(isos.isNotEmpty())

    val win1126h2 = WindowsIsoCatalog.findById("win11-26h2")
    assertNotNull(win1126h2)
    assertEquals("Version 26H2", win1126h2?.versionTag)
    assertEquals(false, win1126h2?.requiresProSubscription)

    val win1125h2 = WindowsIsoCatalog.findById("win11-25h2")
    assertNotNull(win1125h2)
    assertEquals("Version 25H2", win1125h2?.versionTag)
    assertEquals("https://archive.org/details/windows_11_pro_version_25h2_build_26200.7462_full_soft", win1125h2?.officialPortalUrl)

    val win11 = WindowsIsoCatalog.findById("win11-24h2")
    assertNotNull(win11)
    assertEquals("Version 24H2", win11?.versionTag)
    assertEquals("https://archive.org/details/windows-11-24h2-iso_202501", win11?.officialPortalUrl)
    assertEquals(false, win11?.requiresProSubscription)

    val macOsIsos = isos.filter { it.category == IsoCategory.MACOS }
    assertTrue(macOsIsos.size >= 5)
    assertTrue(macOsIsos.all { it.osVendor == OsVendor.APPLE && it.requiresProSubscription })
  }

  @Test
  fun `version selector radio group and dropdown switch windows versions`() {
    var selectedCategory by mutableStateOf(IsoCategory.WINDOWS_11)
    var clickedIsoId: String? = null

    composeTestRule.setContent {
      MyApplicationTheme {
        VersionSelectorPanel(
          selectedCategory = selectedCategory,
          availableIsos = WindowsIsoCatalog.allIsos.filter { it.category == selectedCategory },
          isProSubscribed = false,
          onCategorySelected = { selectedCategory = it },
          onSelectIsoForDownload = { clickedIsoId = it.id }
        )
      }
    }

    composeTestRule.onNodeWithTag("version_selector_panel").assertIsDisplayed()
    composeTestRule.onNodeWithTag("radio_version_windows_10").performClick()
    composeTestRule.waitForIdle()
    assertEquals(IsoCategory.WINDOWS_10, selectedCategory)

    composeTestRule.onNodeWithTag("quick_select_download_btn").performClick()
    assertEquals("win10-22h2", clickedIsoId)
  }

  @Test
  fun `subscription dialog sends pro access email request and shows confirmation`() {
    var requestedPlan: SubscriptionPlan? = null
    var requestedEmail: String? = null

    composeTestRule.setContent {
      MyApplicationTheme {
        SubscriptionDialog(
          currentPlan = SubscriptionPlan.NONE,
          isSubscribed = false,
          userEmail = "",
          isGuest = true,
          proRequestSent = false,
          proRequestEmail = "",
          proRequestRef = "",
          onDismiss = {},
          onRequestProAccess = { plan, email ->
            requestedPlan = plan
            requestedEmail = email
          },
          onClearProRequest = {}
        )
      }
    }

    composeTestRule.onNodeWithTag("subscription_dialog").assertIsDisplayed()
    composeTestRule.onNodeWithTag("plan_option_monthly").performScrollTo().performClick()
    composeTestRule.onNodeWithText("₹199").assertIsDisplayed()

    composeTestRule.onNodeWithTag("pro_request_email_input").performScrollTo().performTextInput("user@example.com")
    composeTestRule.onNodeWithTag("request_pro_email_btn").performScrollTo().performClick()

    assertEquals(SubscriptionPlan.MONTHLY, requestedPlan)
    assertEquals("user@example.com", requestedEmail)
    composeTestRule.onNodeWithTag("pro_email_sent_confirmation").performScrollTo().assertIsDisplayed()
    composeTestRule.onNodeWithText("We sent you an email!").assertIsDisplayed()
  }

  @Test
  fun `downloads screen displays history timestamps and triggers redownload`() {
    var redownloadedId: Int? = null
    val sampleDownloads = listOf(
      DownloadEntity(
        id = 101,
        isoId = "win11-24h2",
        title = "Windows 11 (24H2)",
        edition = "Multi-Edition",
        architecture = "x64 (64-bit)",
        language = "English",
        downloadUrl = "https://software-download.microsoft.com/download/sg/Win11_24H2_English_x64.iso",
        sha256 = "bb25707c",
        status = "DOWNLOADING",
        timestamp = 1763424000000L,
        fileSizeFormatted = "5.4 GB"
      )
    )

    composeTestRule.setContent {
      MyApplicationTheme {
        DownloadsScreen(
          downloads = sampleDownloads,
          bookmarkedIds = emptySet(),
          onReDownload = { redownloadedId = it.id },
          onDeleteDownload = {},
          onClearAllDownloads = {},
          onSelectIso = {}
        )
      }
    }

    composeTestRule.onNodeWithTag("download_history_card_101").assertIsDisplayed()
    composeTestRule.onNodeWithTag("redownload_btn_101").performClick()
    assertEquals(101, redownloadedId)
  }

  @Test
  fun `contact support dialog submits ticket with topic and message`() {
    var submittedTopic: String? = null
    var submittedSubject: String? = null

    composeTestRule.setContent {
      MyApplicationTheme {
        ContactSupportDialog(
          userEmail = "member@outlook.com",
          isGuest = false,
          subscriptionPlan = SubscriptionPlan.ANNUAL,
          recentTickets = emptyList(),
          lastSubmittedTicket = null,
          onDismiss = {},
          onSubmitTicket = { topic, _, subject, _, _ ->
            submittedTopic = topic
            submittedSubject = subject
          },
          onClearLastSubmitted = {}
        )
      }
    }

    composeTestRule.onNodeWithTag("contact_support_dialog").assertIsDisplayed()
    composeTestRule.onNodeWithTag("support_topic_macos_pro").performScrollTo().performClick()
    composeTestRule.onNodeWithTag("support_subject_input").performScrollTo().performTextInput("Sonoma link help")
    composeTestRule.onNodeWithTag("support_message_input").performScrollTo().performTextInput("Need help picking Universal vs IPSW.")
    composeTestRule.onNodeWithTag("support_submit_btn").performScrollTo().performClick()

    assertEquals("macOS Pro", submittedTopic)
    assertEquals("Sonoma link help", submittedSubject)
  }

  @Test
  fun `tools kit screen filters categories and triggers tool download`() {
    var selectedCat by mutableStateOf(ToolkitCategory.ALL)
    var downloadedToolId: String? = null

    composeTestRule.setContent {
      MyApplicationTheme {
        UsbGuideScreen(
          tools = ToolkitCatalog.allTools.filter {
            selectedCat == ToolkitCategory.ALL || it.category == selectedCat
          },
          selectedCategory = selectedCat,
          searchQuery = "",
          onCategorySelect = { selectedCat = it },
          onDownloadTool = { downloadedToolId = it.id }
        )
      }
    }

    composeTestRule.onNodeWithTag("tab_toolkit_downloads").assertIsDisplayed()
    composeTestRule.onNodeWithTag("toolkit_chip_bootable_usb").performScrollTo().performClick()
    composeTestRule.waitForIdle()
    assertEquals(ToolkitCategory.BOOTABLE_USB, selectedCat)

    composeTestRule.onNodeWithTag("download_tool_btn_tool-rufus").performScrollTo().performClick()
    assertEquals("tool-rufus", downloadedToolId)
  }

  @Test
  fun `legal policy dialog shows privacy policy and copyright notice`() {
    var websiteOpened = false

    composeTestRule.setContent {
      MyApplicationTheme {
        LegalPolicyDialog(
          initialTab = 0,
          onDismiss = {},
          onOpenWebsite = { websiteOpened = true }
        )
      }
    }

    composeTestRule.onNodeWithTag("legal_policy_dialog").assertIsDisplayed()
    composeTestRule.onNodeWithText("1. Local Data & Minimal Collection").performScrollTo().assertIsDisplayed()

    composeTestRule.onNodeWithTag("legal_tab_copyright").performScrollTo().performClick()
    composeTestRule.onNodeWithTag("copyright_notice_card").performScrollTo().assertIsDisplayed()
    composeTestRule.onNodeWithText("Copyright © 2026 WinISO Hub. All rights reserved.").assertIsDisplayed()

    composeTestRule.onNodeWithTag("legal_open_website_btn").performScrollTo().performClick()
    assertTrue(websiteOpened)
  }

  @Test
  fun `profile screen other account section displays pro 1 and switches account`() {
    var switchedToPro1 = false
    val guestUser = UserAccount(
      id = "guest_0",
      displayName = "Guest User",
      email = "Local device session",
      provider = AuthProvider.GUEST,
      isGuest = true,
      avatarInitials = "GU",
      joinedDate = "Local Session"
    )

    composeTestRule.setContent {
      MyApplicationTheme {
        ProfileScreen(
          user = guestUser,
          supportTicketCount = 0,
          onOpenSignIn = {},
          onSwitchToPro1 = { switchedToPro1 = true },
          onOpenSubscription = {},
          onOpenContactSupport = {},
          onWifiOnlyChange = {},
          onSignOut = {}
        )
      }
    }

    composeTestRule.onNodeWithTag("profile_other_account_card").assertIsDisplayed()
    composeTestRule.onNodeWithText("Other Account").assertIsDisplayed()
    composeTestRule.onNodeWithText("Pro 1").assertIsDisplayed()
    composeTestRule.onNodeWithTag("profile_switch_pro1_btn").performClick()
    assertTrue(switchedToPro1)
  }

  @Test
  fun `sign in dialog includes pro 1 option under other account`() {
    var pro1Clicked = false

    composeTestRule.setContent {
      MyApplicationTheme {
        SignInDialog(
          onDismiss = {},
          onSignInWithGoogle = { _, _ -> },
          onSignInWithMicrosoft = { _, _ -> },
          onSignInWithEmail = { _, _ -> },
          onSignInWithPro1 = { _, _ -> pro1Clicked = true },
          onContinueAsGuest = {}
        )
      }
    }

    composeTestRule.onNodeWithTag("sign_in_pro1_btn").performScrollTo().performClick()
    assertTrue(pro1Clicked)
  }
}
