package com.tetsoft.typego.keynotes.data

import com.tetsoft.typego.R
import com.tetsoft.typego.keynotes.domain.KeyNotesList
import javax.inject.Inject

class KeyNotesListImpl @Inject constructor() : KeyNotesList {
    override fun get(): List<KeyNote> {
        return listOf(
            KeyNote("language_and_sample_texts_expansion_161", R.drawable.keynote_expansion, R.string.key_note_expansion_161, R.string.key_note_expansion_161_description)
        )
    }
}