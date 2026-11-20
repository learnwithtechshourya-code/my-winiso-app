package com.example.data.util

import android.app.DownloadManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.example.data.model.OsVendor
import com.example.data.model.ToolkitItem
import com.example.data.model.WindowsIso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

object DownloadHelper {

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    fun generateOfficialFileName(
        iso: WindowsIso,
        edition: String,
        architecture: String,
        language: String
    ): String {
        val cleanArch = when {
            architecture.contains("ARM64") -> "ARM64"
            architecture.contains("Universal") -> "Universal"
            architecture.contains("x86") || architecture.contains("32") -> "x86"
            else -> "x64"
        }
        val cleanLang = language.replace(" ", "_").replace("(", "").replace(")", "")
        val baseName = iso.id.replace("-", "_")
        val ext = if (iso.osVendor == OsVendor.APPLE && edition.contains("pkg", ignoreCase = true)) {
            "pkg"
        } else {
            "iso"
        }
        return "${baseName}_${cleanArch}_${cleanLang}.$ext"
    }

    /**
     * Checks if the direct ISO URL responds with HTTP 200 and a binary payload.
     * Session-tokenized Microsoft/Apple CDN links often return 403/404 outside a browser session,
     * which causes Android DownloadManager to show "Download unsuccessful".
     * When a direct link isn't directly downloadable, this opens the official download portal cleanly.
     */
    suspend fun startSmartDownload(
        context: Context,
        iso: WindowsIso,
        edition: String,
        architecture: String,
        language: String,
        downloadUrl: String
    ): String {
        val isDirectBinaryReachable = withContext(Dispatchers.IO) {
            isUrlDirectDownloadable(downloadUrl)
        }

        if (isDirectBinaryReachable) {
            val fileName = generateOfficialFileName(iso, edition, architecture, language)
            val enqueued = enqueueSystemDownload(
                context = context,
                fileName = fileName,
                description = "Official ${iso.osVendor.vendorName} OS Image",
                downloadUrl = downloadUrl
            )
            if (enqueued != null) {
                return "DOWNLOADING"
            }
        }

        withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                "Opening official ${iso.osVendor.vendorName} page to start your download safely.",
                Toast.LENGTH_LONG
            ).show()
            openOfficialPortal(context, iso.officialPortalUrl)
        }
        return "OPENED_PORTAL"
    }

    suspend fun startToolkitDownload(
        context: Context,
        tool: ToolkitItem
    ): String {
        val isDirectBinaryReachable = withContext(Dispatchers.IO) {
            isUrlDirectDownloadable(tool.directDownloadUrl)
        }

        if (isDirectBinaryReachable) {
            val cleanName = tool.name.lowercase()
                .replace(" ", "_")
                .replace("-", "_")
            val fileName = "${cleanName}_v${tool.version}.${tool.fileExtension}"
            val enqueued = enqueueSystemDownload(
                context = context,
                fileName = fileName,
                description = "${tool.vendor} Official Toolkit",
                downloadUrl = tool.directDownloadUrl
            )
            if (enqueued != null) {
                return "DOWNLOADING"
            }
        }

        withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                "Opening official ${tool.name} page to start download.",
                Toast.LENGTH_LONG
            ).show()
            openOfficialPortal(context, tool.officialPortalUrl)
        }
        return "OPENED_PORTAL"
    }

    private fun isUrlDirectDownloadable(url: String): Boolean {
        return try {
            val headRequest = Request.Builder()
                .url(url)
                .head()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .build()
            httpClient.newCall(headRequest).execute().use { response ->
                val contentType = response.header("Content-Type")?.lowercase() ?: ""
                val isHtml = contentType.contains("text/html")
                response.isSuccessful && !isHtml
            }
        } catch (_: Exception) {
            false
        }
    }

    private fun enqueueSystemDownload(
        context: Context,
        fileName: String,
        description: String,
        downloadUrl: String
    ): Long? {
        return try {
            val uri = Uri.parse(downloadUrl)

            val request = DownloadManager.Request(uri)
                .setTitle("Downloading $fileName")
                .setDescription(description)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(false)

            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = downloadManager.enqueue(request)

            Toast.makeText(
                context,
                "Download started: $fileName",
                Toast.LENGTH_LONG
            ).show()

            downloadId
        } catch (_: Exception) {
            null
        }
    }

    fun copyToClipboard(context: Context, text: String, label: String = "Download Link") {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "$label copied", Toast.LENGTH_SHORT).show()
    }

    fun openOfficialPortal(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open browser: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareIsoLink(context: Context, isoTitle: String, url: String, sha256: String) {
        val shareText = """
            $isoTitle
            Official Portal / Link: $url
            SHA-256: $sha256
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "$isoTitle - Official ISO Link")
            putExtra(Intent.EXTRA_TEXT, shareText)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val chooser = Intent.createChooser(intent, "Send ISO Link")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }

    fun shareToolkitLink(context: Context, tool: ToolkitItem) {
        val shareText = """
            ${tool.name} v${tool.version} (${tool.vendor})
            Direct Link: ${tool.directDownloadUrl}
            Official Page: ${tool.officialPortalUrl}
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "${tool.name} - Official Toolkit Link")
            putExtra(Intent.EXTRA_TEXT, shareText)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val chooser = Intent.createChooser(intent, "Send Tool Link to PC")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }

    fun sendSupportEmail(
        context: Context,
        recipient: String = "learnwithtechshourya@gmail.com",
        subject: String,
        body: String
    ) {
        try {
            val mailUri = Uri.parse("mailto:$recipient?subject=${Uri.encode(subject)}&body=${Uri.encode(body)}")
            val emailIntent = Intent(Intent.ACTION_SENDTO, mailUri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(emailIntent)
        } catch (_: Exception) {
            copyToClipboard(context, "$recipient\n\n$subject\n\n$body", "Support Message")
            Toast.makeText(
                context,
                "No email app found. Support email & message copied to clipboard.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
