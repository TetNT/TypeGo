package com.tetsoft.typego.releasenotes.domain

interface ReleaseNotesList {
    fun get(): List<ReleaseNote>

    fun getReversed() : List<ReleaseNote> {
        return get().reversed()
    }
}

