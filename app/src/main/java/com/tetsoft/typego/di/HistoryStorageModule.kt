package com.tetsoft.typego.di

import com.tetsoft.typego.data.history.GameHistory
import com.tetsoft.typego.storage.history.RandomWordsHistoryStorage
import com.tetsoft.typego.storage.history.OwnTextGameHistoryStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HistoryStorageModule {
    @Binds
    fun bindGameOnTimeHistoryStorage(randomWordsHistoryStorage: RandomWordsHistoryStorage.SharedPreferences): RandomWordsHistoryStorage

    @Binds
    fun bindOwnTextGameHistoryStorage(ownTextGameHistoryStorage: OwnTextGameHistoryStorage.SharedPreferences): OwnTextGameHistoryStorage

    @Binds
    fun bindGameHistory(gameHistory: GameHistory.Standard) : GameHistory
}