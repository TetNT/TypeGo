package com.tetsoft.typego.data

import android.content.Context
import androidx.annotation.StringRes
import com.tetsoft.typego.R

interface ReleaseNotesList {
    fun get(): List<ReleaseNote>

    fun getReversed() : List<ReleaseNote> {
        return get().reversed()
    }

    class Standard(private val context: Context) : ReleaseNotesList {
        override fun get(): List<ReleaseNote> {
            return listOf(
                ReleaseNote.Standard(context, "1.4.0", R.string.release_notes_description_140),
                ReleaseNote.Standard(context, "1.4.3", R.string.release_notes_description_143),
                ReleaseNote.Standard(context, "1.4.7", R.string.release_notes_description_147),
                ReleaseNote.Standard(context, "1.4.8", R.string.release_notes_description_148),
                ReleaseNote.Standard(context, "1.4.9", R.string.release_notes_description_149),
                ReleaseNote.Standard(context, "1.5.0", R.string.release_notes_description_150),
                ReleaseNote.Standard(context, "1.5.1", R.string.release_notes_description_151),
            )
        }
    }

    class Mock : ReleaseNotesList {
        override fun get(): List<ReleaseNote> {
            return listOf(
                ReleaseNote.Mock("1.0.0", "Description for 1.0.0"),
                ReleaseNote.Mock("1.0.1", "Description for 1.0.1"),
                ReleaseNote.Mock("1.0.2", "Description for 1.0.2"),
                ReleaseNote.Mock("1.1.0", "Description for 1.1.0"),
                ReleaseNote.Mock("2.0.0", "Description for 2.0.0"),
            )
        }
    }
}

interface ReleaseNote {
    fun getVersion(): String

    fun getDescription(): String

    fun getText() : CharSequence

    class Standard(
        private val context: Context,
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

    class Mock(private val version: String, private val description: String) : ReleaseNote {
        override fun getVersion(): String = version

        override fun getDescription(): String = description

        override fun getText(): CharSequence {
            return description
        }
    }
}