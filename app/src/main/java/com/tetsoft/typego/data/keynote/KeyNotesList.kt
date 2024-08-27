package com.tetsoft.typego.data.keynote

import com.tetsoft.typego.R
import javax.inject.Inject

interface KeyNotesList {
    fun get() : List<KeyNote>

    class Standard @Inject constructor() : KeyNotesList {
        override fun get(): List<KeyNote> {
            return listOf(
                KeyNote("new_game_mode_160", R.drawable.text_filled, R.string.key_note_new_game_mode, R.string.key_note_new_game_mode_description),
                KeyNote("new_theme_160", R.drawable.button_lilac, R.string.key_note_new_theme, R.string.key_note_new_theme_description),
            )
        }
    }
}