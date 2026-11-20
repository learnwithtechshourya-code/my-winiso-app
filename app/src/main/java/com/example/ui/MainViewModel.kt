package com.example.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.DownloadEntity
import com.example.data.local.SupportTicketEntity
import com.example.data.model.AuthProvider
import com.example.data.model.IsoCategory
import com.example.data.model.LanguageOption
import com.example.data.model.OfficialLanguages
import com.example.data.model.SubscriptionPlan
import com.example.data.model.ToolkitCategory
import com.example.data.model.ToolkitItem
import com.example.data.model.UserAccount
import com.example.data.model.WindowsIso
import com.example.data.repository.AuthRepository
import com.example.data.repository.IsoRepository
import com.example.data.repository.ToolkitCatalog
import com.example.data.repository.WindowsIsoCatalog
import com.example.data.util.DownloadHelper
import com.example.data.util.HashCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MainNavTab {
    EXPLORE,
    VERIFIER,
    USB_GUIDE,
    DOWNLOADS,
    PROFILE
}

sealed class HashVerifyResult {
    object Idle : HashVerifyResult()
    data class Matched(val iso: WindowsIso, val hash: String) : HashVerifyResult()
    data class NotMatched(val inputHash: String) : HashVerifyResult()
    data class Error(val message: String) : HashVerifyResult()
}

class MainViewModel(
    private val isoRepository: IsoRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    // Navigation Tab
    private val _selectedTab = MutableStateFlow(MainNavTab.EXPLORE)
    val selectedTab: StateFlow<MainNavTab> = _selectedTab.asStateFlow()

    fun selectTab(tab: MainNavTab) {
        _selectedTab.value = tab
    }

    // Category filter & Search
    private val _selectedCategory = MutableStateFlow(IsoCategory.ALL)
    val selectedCategory: StateFlow<IsoCategory> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun setCategory(category: IsoCategory) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Filtered ISOs
    val filteredIsos: StateFlow<List<WindowsIso>> = combine(_selectedCategory, _searchQuery) { cat, q ->
        isoRepository.filterIsos(cat, q)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), isoRepository.getCatalogIsos())

    // Tools Kit Filter & Search
    private val _selectedToolkitCategory = MutableStateFlow(ToolkitCategory.ALL)
    val selectedToolkitCategory: StateFlow<ToolkitCategory> = _selectedToolkitCategory.asStateFlow()

    private val _toolkitSearchQuery = MutableStateFlow("")
    val toolkitSearchQuery: StateFlow<String> = _toolkitSearchQuery.asStateFlow()

    fun setToolkitCategory(category: ToolkitCategory) {
        _selectedToolkitCategory.value = category
    }

    fun setToolkitSearchQuery(query: String) {
        _toolkitSearchQuery.value = query
    }

    val filteredTools: StateFlow<List<ToolkitItem>> = combine(_selectedToolkitCategory, _toolkitSearchQuery) { cat, q ->
        isoRepository.filterTools(cat, q)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), isoRepository.getToolkitItems())

    // Bookmarked ISO IDs
    val bookmarkedIds: StateFlow<Set<String>> = isoRepository.allBookmarks
        .map { list -> list.map { it.isoId }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    fun toggleBookmark(isoId: String) {
        viewModelScope.launch {
            val isCurrent = bookmarkedIds.value.contains(isoId)
            isoRepository.toggleBookmark(isoId, isCurrent)
        }
    }

    // Downloads history
    val downloadsList: StateFlow<List<DownloadEntity>> = isoRepository.allDownloads
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteDownload(id: Int) {
        viewModelScope.launch {
            isoRepository.deleteDownload(id)
        }
    }

    fun clearAllDownloads() {
        viewModelScope.launch {
            isoRepository.clearAllDownloads()
        }
    }

    // User Account & Sign In Options
    val currentUser: StateFlow<UserAccount> = authRepository.currentUser
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UserAccount(
                id = "guest_0",
                displayName = "Guest User",
                email = "Local device session",
                provider = AuthProvider.GUEST,
                isGuest = true,
                avatarInitials = "GU",
                joinedDate = "Local Session"
            )
        )

    private val _showSignInDialog = MutableStateFlow(false)
    val showSignInDialog: StateFlow<Boolean> = _showSignInDialog.asStateFlow()

    fun openSignInDialog() {
        _showSignInDialog.value = true
    }

    fun closeSignInDialog() {
        _showSignInDialog.value = false
    }

    fun signInWithGoogle(displayName: String, email: String) {
        viewModelScope.launch {
            authRepository.signInWithGoogle(displayName, email)
            _showSignInDialog.value = false
        }
    }

    fun signInWithMicrosoft(displayName: String, email: String) {
        viewModelScope.launch {
            authRepository.signInWithMicrosoft(displayName, email)
            _showSignInDialog.value = false
        }
    }

    fun signInWithEmail(displayName: String, email: String) {
        viewModelScope.launch {
            authRepository.signInWithEmail(displayName, email)
            _showSignInDialog.value = false
        }
    }

    fun signInWithPro1(
        displayName: String = "Pro 1 Account",
        email: String = "pro1@local"
    ) {
        viewModelScope.launch {
            authRepository.signInWithPro1(displayName, email)
            _showSignInDialog.value = false
        }
    }

    fun continueAsGuest() {
        viewModelScope.launch {
            authRepository.continueAsGuest()
            _showSignInDialog.value = false
        }
    }

    fun updateWifiOnlyPreference(wifiOnly: Boolean) {
        viewModelScope.launch {
            val user = currentUser.value
            authRepository.updatePreferences(
                arch = user.preferredArchitecture,
                lang = user.preferredLanguage,
                wifiOnly = wifiOnly
            )
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }

    // macOS Pro Subscription Dialog & State
    private val _showSubscriptionDialog = MutableStateFlow(false)
    val showSubscriptionDialog: StateFlow<Boolean> = _showSubscriptionDialog.asStateFlow()

    fun openSubscriptionDialog() {
        _showSubscriptionDialog.value = true
    }

    fun closeSubscriptionDialog() {
        _showSubscriptionDialog.value = false
    }

    fun submitProAccessRequest(
        plan: SubscriptionPlan,
        email: String
    ) {
        viewModelScope.launch {
            val refCode = "PRO-${(1000..9999).random()}"
            authRepository.submitProAccessRequest(plan, email, refCode)
            isoRepository.submitSupportTicket(
                SupportTicketEntity(
                    referenceCode = refCode,
                    topic = "macOS Pro",
                    email = email,
                    subject = "Pro Access Request: ${plan.displayName} (${plan.priceLabel})",
                    message = "Requested ${plan.displayName} (${plan.priceLabel}). Confirmation email sent to $email.",
                    diagnosticsSummary = "Plan: ${plan.displayName} | Price: ${plan.priceLabel}"
                )
            )
        }
    }

    fun clearProAccessRequest() {
        viewModelScope.launch {
            authRepository.clearProAccessRequest()
        }
    }

    // Contact Support Dialog & Ticket History
    val supportTickets: StateFlow<List<SupportTicketEntity>> = isoRepository.allSupportTickets
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _showContactSupportDialog = MutableStateFlow(false)
    val showContactSupportDialog: StateFlow<Boolean> = _showContactSupportDialog.asStateFlow()

    private val _lastSubmittedTicket = MutableStateFlow<SupportTicketEntity?>(null)
    val lastSubmittedTicket: StateFlow<SupportTicketEntity?> = _lastSubmittedTicket.asStateFlow()

    fun openContactSupportDialog() {
        _showContactSupportDialog.value = true
    }

    fun closeContactSupportDialog() {
        _showContactSupportDialog.value = false
    }

    fun clearLastSubmittedTicket() {
        _lastSubmittedTicket.value = null
    }

    // Legal (Privacy Policy & Copyright) and In-App Website Dialogs
    private val _legalDialogInitialTab = MutableStateFlow<Int?>(null)
    val legalDialogInitialTab: StateFlow<Int?> = _legalDialogInitialTab.asStateFlow()

    private val _showWebsiteDialog = MutableStateFlow(false)
    val showWebsiteDialog: StateFlow<Boolean> = _showWebsiteDialog.asStateFlow()

    fun openPrivacyPolicy() {
        _legalDialogInitialTab.value = 0
    }

    fun openCopyrightNotice() {
        _legalDialogInitialTab.value = 1
    }

    fun closeLegalDialog() {
        _legalDialogInitialTab.value = null
    }

    fun openWebsiteDialog() {
        _showWebsiteDialog.value = true
    }

    fun closeWebsiteDialog() {
        _showWebsiteDialog.value = false
    }

    fun submitSupportTicket(
        topic: String,
        email: String,
        subject: String,
        message: String,
        diagnostics: String
    ) {
        viewModelScope.launch {
            val code = "SUP-${(1000..9999).random()}"
            val ticket = SupportTicketEntity(
                referenceCode = code,
                topic = topic,
                email = email,
                subject = subject,
                message = message,
                diagnosticsSummary = diagnostics
            )
            isoRepository.submitSupportTicket(ticket)
            _lastSubmittedTicket.value = ticket
        }
    }

    // Selected ISO Detail Dialog & Download Customization
    private val _selectedIso = MutableStateFlow<WindowsIso?>(null)
    val selectedIso: StateFlow<WindowsIso?> = _selectedIso.asStateFlow()

    private val _selectedArchitecture = MutableStateFlow("x64 (64-bit)")
    val selectedArchitecture: StateFlow<String> = _selectedArchitecture.asStateFlow()

    private val _selectedEdition = MutableStateFlow("")
    val selectedEdition: StateFlow<String> = _selectedEdition.asStateFlow()

    private val _selectedLanguage = MutableStateFlow(OfficialLanguages.first())
    val selectedLanguage: StateFlow<LanguageOption> = _selectedLanguage.asStateFlow()

    fun openIsoDetail(iso: WindowsIso) {
        _selectedIso.value = iso
        _selectedArchitecture.value = iso.supportedArchitectures.firstOrNull() ?: "x64 (64-bit)"
        _selectedEdition.value = iso.editions.firstOrNull() ?: "Official Edition"
        _selectedLanguage.value = OfficialLanguages.first()
    }

    fun handleIsoDownloadClick(iso: WindowsIso) {
        if (iso.requiresProSubscription && !currentUser.value.isProSubscribed) {
            _showSubscriptionDialog.value = true
        } else {
            openIsoDetail(iso)
        }
    }

    fun closeIsoDetail() {
        _selectedIso.value = null
    }

    fun setArchitecture(arch: String) {
        _selectedArchitecture.value = arch
    }

    fun setEdition(edition: String) {
        _selectedEdition.value = edition
    }

    fun setLanguage(lang: LanguageOption) {
        _selectedLanguage.value = lang
    }

    fun startDownload(context: Context) {
        val iso = _selectedIso.value ?: return
        if (iso.requiresProSubscription && !currentUser.value.isProSubscribed) {
            _selectedIso.value = null
            _showSubscriptionDialog.value = true
            return
        }

        val edition = _selectedEdition.value
        val arch = _selectedArchitecture.value
        val lang = _selectedLanguage.value.name
        val url = iso.directDownloadUrlTemplate

        viewModelScope.launch {
            val status = DownloadHelper.startSmartDownload(
                context = context,
                iso = iso,
                edition = edition,
                architecture = arch,
                language = lang,
                downloadUrl = url
            )

            isoRepository.recordDownload(
                iso = iso,
                edition = edition,
                architecture = arch,
                language = lang,
                downloadUrl = if (status == "DOWNLOADING") url else iso.officialPortalUrl,
                status = status
            )
        }
    }

    fun downloadToolkit(context: Context, tool: ToolkitItem) {
        viewModelScope.launch {
            val status = DownloadHelper.startToolkitDownload(context, tool)
            isoRepository.recordToolkitDownload(
                tool = tool,
                downloadUrl = if (status == "DOWNLOADING") tool.directDownloadUrl else tool.officialPortalUrl,
                status = status
            )
        }
    }

    fun shareToolkit(context: Context, tool: ToolkitItem) {
        DownloadHelper.shareToolkitLink(context, tool)
    }

    fun reDownloadItem(context: Context, item: DownloadEntity) {
        val toolkitItem = ToolkitCatalog.findById(item.isoId)
        if (toolkitItem != null) {
            viewModelScope.launch {
                val status = DownloadHelper.startToolkitDownload(context, toolkitItem)
                isoRepository.recordToolkitDownload(
                    tool = toolkitItem,
                    downloadUrl = if (status == "DOWNLOADING") toolkitItem.directDownloadUrl else toolkitItem.officialPortalUrl,
                    status = if (status == "DOWNLOADING") "RE-DOWNLOADED" else "OPENED_PORTAL"
                )
            }
            return
        }

        val catalogIso = WindowsIsoCatalog.findById(item.isoId)
        if (catalogIso != null && catalogIso.requiresProSubscription && !currentUser.value.isProSubscribed) {
            _showSubscriptionDialog.value = true
            return
        }

        val targetIso = catalogIso ?: WindowsIso(
            id = item.isoId,
            title = item.title,
            versionTag = item.edition,
            buildNumber = item.architecture,
            category = IsoCategory.ALL,
            releaseDate = "Official",
            estimatedSize = item.fileSizeFormatted,
            supportedArchitectures = listOf(item.architecture),
            editions = listOf(item.edition),
            officialPortalUrl = item.downloadUrl,
            directDownloadUrlTemplate = item.downloadUrl,
            officialSha256 = item.sha256,
            features = emptyList(),
            systemRequirements = emptyList()
        )

        viewModelScope.launch {
            val status = DownloadHelper.startSmartDownload(
                context = context,
                iso = targetIso,
                edition = item.edition,
                architecture = item.architecture,
                language = item.language,
                downloadUrl = item.downloadUrl
            )

            isoRepository.recordDownload(
                iso = targetIso,
                edition = item.edition,
                architecture = item.architecture,
                language = item.language,
                downloadUrl = if (status == "DOWNLOADING") item.downloadUrl else targetIso.officialPortalUrl,
                status = if (status == "DOWNLOADING") "RE-DOWNLOADED" else "OPENED_PORTAL"
            )
        }
    }

    fun copyDownloadLink(context: Context) {
        val iso = _selectedIso.value ?: return
        if (iso.requiresProSubscription && !currentUser.value.isProSubscribed) {
            _selectedIso.value = null
            _showSubscriptionDialog.value = true
            return
        }

        val url = iso.directDownloadUrlTemplate
        DownloadHelper.copyToClipboard(context, url, "${iso.title} Link")
        viewModelScope.launch {
            isoRepository.recordDownload(
                iso = iso,
                edition = _selectedEdition.value,
                architecture = _selectedArchitecture.value,
                language = _selectedLanguage.value.name,
                downloadUrl = url,
                status = "LINK_COPIED"
            )
        }
    }

    fun openOfficialPortal(context: Context, url: String) {
        DownloadHelper.openOfficialPortal(context, url)
    }

    fun shareDownload(context: Context) {
        val iso = _selectedIso.value ?: return
        if (iso.requiresProSubscription && !currentUser.value.isProSubscribed) {
            _selectedIso.value = null
            _showSubscriptionDialog.value = true
            return
        }
        DownloadHelper.shareIsoLink(
            context = context,
            isoTitle = iso.title,
            url = iso.directDownloadUrlTemplate,
            sha256 = iso.officialSha256
        )
    }

    // Hash Verifier State
    private val _inputHash = MutableStateFlow("")
    val inputHash: StateFlow<String> = _inputHash.asStateFlow()

    private val _hashVerifyResult = MutableStateFlow<HashVerifyResult>(HashVerifyResult.Idle)
    val hashVerifyResult: StateFlow<HashVerifyResult> = _hashVerifyResult.asStateFlow()

    private val _isHashingFile = MutableStateFlow(false)
    val isHashingFile: StateFlow<Boolean> = _isHashingFile.asStateFlow()

    private val _hashingProgress = MutableStateFlow(0f)
    val hashingProgress: StateFlow<Float> = _hashingProgress.asStateFlow()

    fun setInputHash(hash: String) {
        _inputHash.value = hash
    }

    fun verifyInputHash() {
        val hash = _inputHash.value.trim().lowercase()
        if (hash.isBlank()) {
            _hashVerifyResult.value = HashVerifyResult.Error("Enter or paste a 64-character SHA-256 hash.")
            return
        }
        val match = WindowsIsoCatalog.verifyHash(hash)
        if (match != null) {
            _hashVerifyResult.value = HashVerifyResult.Matched(match, hash)
        } else {
            _hashVerifyResult.value = HashVerifyResult.NotMatched(hash)
        }
    }

    fun verifyFile(context: Context, uri: Uri) {
        _isHashingFile.value = true
        _hashingProgress.value = 0f
        _hashVerifyResult.value = HashVerifyResult.Idle

        viewModelScope.launch {
            val result = HashCalculator.calculateSha256(context, uri) { progress ->
                _hashingProgress.value = progress
            }
            _isHashingFile.value = false

            result.onSuccess { calculatedHash ->
                _inputHash.value = calculatedHash
                val match = WindowsIsoCatalog.verifyHash(calculatedHash)
                if (match != null) {
                    _hashVerifyResult.value = HashVerifyResult.Matched(match, calculatedHash)
                } else {
                    _hashVerifyResult.value = HashVerifyResult.NotMatched(calculatedHash)
                }
            }.onFailure { error ->
                _hashVerifyResult.value = HashVerifyResult.Error("Unable to read file: ${error.message}")
            }
        }
    }

    fun clearHashVerification() {
        _inputHash.value = ""
        _hashVerifyResult.value = HashVerifyResult.Idle
        _isHashingFile.value = false
        _hashingProgress.value = 0f
    }
}

class MainViewModelFactory(
    private val isoRepository: IsoRepository,
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(isoRepository, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
