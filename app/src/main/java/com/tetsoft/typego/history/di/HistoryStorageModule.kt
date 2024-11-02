package com.tetsoft.typego.history.di

import com.tetsoft.typego.history.domain.GameHistory
import com.tetsoft.typego.history.data.GameHistoryImpl
import com.tetsoft.typego.history.data.RandomWordsHistoryStorage
import com.tetsoft.typego.history.data.OwnTextGameHistoryStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
// FIXME: Make DI inside of the SharedPreferences so this bind can be used
@Module
@InstallIn(SingletonComponent::class)
interface HistoryStorageModule {
    @Binds
    fun bindRandomWordsHistoryStorage(randomWordsHistoryStorage: RandomWordsHistoryStorage.SharedPreferences): RandomWordsHistoryStorage

    @Binds
    fun bindOwnTextGameHistoryStorage(ownTextGameHistoryStorage: OwnTextGameHistoryStorage.SharedPreferences): OwnTextGameHistoryStorage

    @Binds
    fun bindGameHistory(gameHistory: GameHistoryImpl) : GameHistory
}