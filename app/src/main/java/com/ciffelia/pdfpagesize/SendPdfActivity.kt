package com.ciffelia.pdfpagesize

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class SendPdfActivity : AppCompatActivity(R.layout.activity_send_pdf) {

    private val viewModelFactory by lazy { SendPdfViewModelFactory(application, intent) }
    private val viewModel: SendPdfViewModel by viewModels { viewModelFactory }

    private val titleTextView: TextView by lazy { findViewById(R.id.titleTextView) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.pageRecyclerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        displayPdfFileName()
        displayPdfMetadata()
    }

    fun closeActivity(@Suppress("UNUSED_PARAMETER") view: View) {
        finish()
    }

    private fun displayPdfFileName() {
        viewModel.pdfFileName?.let {
            titleTextView.text = it
        }
    }

    private fun displayPdfMetadata() {
        recyclerView.apply {
            adapter = PageRecyclerViewAdapter(this@SendPdfActivity, viewModel.pdfFile.pages)
        }
    }
}
