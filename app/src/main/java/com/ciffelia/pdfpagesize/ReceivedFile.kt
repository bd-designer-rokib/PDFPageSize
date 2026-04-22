package com.ciffelia.pdfpagesize

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.provider.OpenableColumns

class ReceivedFile(private val context: Context, private val intent: Intent) {

    private val uri: Uri = run {
        when (intent.action) {
            Intent.ACTION_SEND -> {
                if (Build.VERSION.SDK_INT >= 33) {
                    intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra(Intent.EXTRA_STREAM)
                }
            }
            Intent.ACTION_VIEW -> intent.data
            else -> throw IllegalArgumentException("Unknown intent")
        } ?: throw IllegalArgumentException("No URI in intent")
    }

    val fileDescriptor: ParcelFileDescriptor = run {
        context.contentResolver.openFileDescriptor(uri, "r")
            ?: throw IllegalArgumentException("Invalid URI")
    }

    val fileName: String? = run {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            cursor.getString(nameIndex)
        }
    }
}
