package com.tetsoft.typego.keynotes.data

import com.tetsoft.typego.R
import com.tetsoft.typego.keynotes.domain.KeyNotesList
import javax.inject.Inject

class KeyNotesListImpl @Inject constructor() : KeyNotesList {
    override fun get(): List<KeyNote> {
        return listOf(
            KeyNote("language_expansion_161", R.drawable.keynote_expansion, R.string.keynote_language_expansion_161, R.string.keynote_language_expansion_161_description),
            KeyNote("sample_texts_expansion_161", R.drawable.ic_achievement_typewriter, R.string.keynote_more_sample_texts_161, R.string.keynote_more_sample_texts_161_description),
        )
    }
}