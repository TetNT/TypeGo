package com.tetsoft.typego.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.tetsoft.typego.BuildConfig
import com.tetsoft.typego.R

class ReleaseNotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release_notes)
        val tvCurrentVersion : TextView = findViewById(R.id.tv_whats_new)
        tvCurrentVersion.text = getString(R.string.whats_new_pl, BuildConfig.VERSION_NAME)
    }
}