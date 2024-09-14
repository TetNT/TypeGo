package com.tetsoft.typego.keynotes.di

import com.tetsoft.typego.keynotes.data.KeyNotesStateStorage
import com.tetsoft.typego.keynotes.data.KeyNotesList
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