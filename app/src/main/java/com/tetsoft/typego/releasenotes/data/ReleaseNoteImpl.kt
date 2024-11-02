package com.tetsoft.typego.releasenotes.data

import android.content.Context
import androidx.annotation.StringRes
import com.tetsoft.typego.releasenotes.domain.ReleaseNote

class ReleaseNoteImpl(private val context: Context,
                      private val version: String,
                      @StringRes private val description: Int
) : ReleaseNote {
    override fun getVersion(): String {
        return version
    }

    override fun getDescription(): String {
        return context.getString(description)
    }

    override fun getText(): CharSequence {
        return context.getText(description)
    }
}

class ReleaseNoteMock(private val version: String, private val description: String) : ReleaseNote {
    override fun getVersion(): String = version

    override fun getDescription(): String = description

    override fun getText(): CharSequence {
        return description
    }
}