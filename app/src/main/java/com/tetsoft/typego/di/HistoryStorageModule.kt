package com.tetsoft.typego.di

import com.tetsoft.typego.storage.history.GameOnNumberOfWordsHistoryStorage
import com.tetsoft.typego.storage.history.GameOnTimeHistoryStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HistoryStorageModule {
    @Binds
    fun bindGameOnTimeHistoryStorage(gameOnTimeHistoryStorage: GameOnTimeHistoryStorage.Mock) : GameOnTimeHistoryStorage

    @Binds
    fun bindGameOnNumberOfWordsHistoryStorage(gameOnNumberOfWordsHistoryStorage : GameOnNumberOfWordsHistoryStorage.Mock) : GameOnNumberOfWordsHistoryStorage
}