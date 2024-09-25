package com.tetsoft.typego.keynotes.data

import com.tetsoft.typego.R
import com.tetsoft.typego.keynotes.domain.KeyNotesList
import javax.inject.Inject

class KeyNotesListImpl @Inject constructor() : KeyNotesList {
    override fun get(): List<KeyNote> {
        return listOf(
            KeyNote("new_game_mode_160", R.drawable.text_filled, R.string.key_note_new_game_mode, R.string.key_note_new_game_mode_description),
            KeyNote("new_theme_160", R.drawable.button_lilac, R.string.key_note_new_theme, R.string.key_note_new_theme_description),
        )
    }
}