package com.tetsoft.typego.keynotes.domain

import com.tetsoft.typego.keynotes.data.KeyNote

interface KeyNotesList {
    fun get() : List<KeyNote>
}