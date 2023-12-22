package com.tetsoft.typego.di

import com.tetsoft.typego.storage.KeyNotesStateStorage
import com.tetsoft.typego.data.keynote.KeyNotesList
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface KeyNotesStateStorageBindModule {

    @Binds
    fun bindKeyNotesStateStorage(keyNotesStateStorage: KeyNotesStateStorage.Standard) : KeyNotesStateStorage

    @Binds
    fun bindKeyNotesList(keyNotesList: KeyNotesList.Standard) : KeyNotesList
}