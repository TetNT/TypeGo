package com.tetsoft.typego.releasenotes.data

import android.content.Context
import com.tetsoft.typego.R
import com.tetsoft.typego.releasenotes.domain.ReleaseNote
import com.tetsoft.typego.releasenotes.domain.ReleaseNotesList

class ReleaseNotesListImpl(private val context: Context) : ReleaseNotesList {
    override fun get(): List<ReleaseNote> {
        return listOf(
            ReleaseNoteImpl(context, "1.4.0", R.string.release_notes_description_140),
            ReleaseNoteImpl(context, "1.4.3", R.string.release_notes_description_143),
            ReleaseNoteImpl(context, "1.4.7", R.string.release_notes_description_147),
            ReleaseNoteImpl(context, "1.4.8", R.string.release_notes_description_148),
            ReleaseNoteImpl(context, "1.4.9", R.string.release_notes_description_149),
            ReleaseNoteImpl(context, "1.5.0", R.string.release_notes_description_150),
            ReleaseNoteImpl(context, "1.5.1", R.string.release_notes_description_151),
            ReleaseNoteImpl(context, "1.5.2", R.string.release_notes_description_152),
            ReleaseNoteImpl(context, "1.5.3", R.string.release_notes_description_153),
            ReleaseNoteImpl(context, "1.6.0", R.string.release_notes_description_160)
        )
    }
}

class ReleaseNotesListMock : ReleaseNotesList {
    override fun get(): List<ReleaseNote> {
        return listOf(
            ReleaseNoteMock("1.0.0", "Description for 1.0.0"),
            ReleaseNoteMock("1.0.1", "Description for 1.0.1"),
            ReleaseNoteMock("1.0.2", "Description for 1.0.2"),
            ReleaseNoteMock("1.1.0", "Description for 1.1.0"),
            ReleaseNoteMock("2.0.0", "Description for 2.0.0"),
        )
    }
}