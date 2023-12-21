package com.tetsoft.typego.data.keynote

import com.tetsoft.typego.R
import javax.inject.Inject

interface KeyNotesList {
    fun get() : List<KeyNote>

    class Standard @Inject constructor() : KeyNotesList {
        override fun get(): List<KeyNote> {
            return listOf(
                KeyNote("redesign_153", R.drawable.ic_achievement_alien, R.string.key_note_redesign, R.string.key_note_redesign_description),
                KeyNote("seeds_introduction_153", R.drawable.ic_key, R.string.key_note_seeds, R.string.key_note_seeds_description),
            )
        }
    }
}