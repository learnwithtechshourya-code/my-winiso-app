package com.example.data.util

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest

object HashCalculator {

    suspend fun calculateSha256(
        context: Context,
        uri: Uri,
        onProgress: (Float) -> Unit = {}
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(uri)
                ?: return@withContext Result.failure(Exception("Cannot open file stream"))

            val digest = MessageDigest.getInstance("SHA-256")
            val buffer = ByteArray(64 * 1024)
            var bytesRead: Int

            val totalBytes = try {
                contentResolver.openFileDescriptor(uri, "r")?.statSize ?: -1L
            } catch (e: Exception) {
                -1L
            }

            var readAccumulator = 0L

            inputStream.use { stream ->
                while (stream.read(buffer).also { bytesRead = it } != -1) {
                    digest.update(buffer, 0, bytesRead)
                    readAccumulator += bytesRead
                    if (totalBytes > 0) {
                        val progress = (readAccumulator.toFloat() / totalBytes).coerceIn(0f, 1f)
                        onProgress(progress)
                    }
                }
            }

            val hashBytes = digest.digest()
            val hexString = hashBytes.joinToString("") { "%02x".format(it) }
            Result.success(hexString)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun generatePowerShellCommand(fileName: String = "Win11_English_x64.iso"): String {
        return "Get-FileHash -Path \".\\$fileName\" -Algorithm SHA256"
    }

    fun generateBashCommand(fileName: String = "Win11_English_x64.iso"): String {
        return "sha256sum \"$fileName\""
    }

    fun generateCmdCertUtil(fileName: String = "Win11_English_x64.iso"): String {
        return "certutil -hashfile \"$fileName\" SHA256"
    }
}
