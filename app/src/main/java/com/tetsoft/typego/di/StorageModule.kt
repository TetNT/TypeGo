package com.tetsoft.typego.di

import android.content.Context
import com.tetsoft.typego.data.achievement.AchievementsList
import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.GameResultListStorage
import com.tetsoft.typego.storage.history.GameOnNumberOfWordsHistoryStorage
import com.tetsoft.typego.storage.history.GameOnTimeHistoryStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun provideGameResultListStorage(@ApplicationContext context: Context): GameResultListStorage {
        return GameResultListStorage(context)
    }

    @Provides
    @Singleton
    fun provideGameOnTimeHistoryStorage(@ApplicationContext context: Context): GameOnTimeHistoryStorage {
        //TODO: uncomment this line after testing return GameOnTimeHistoryStorage(context)
        return GameOnTimeHistoryStorage.Mock(context)
    }

    @Provides
    @Singleton
    fun provideGameOnNumberOfWordsHistoryStorage(@ApplicationContext context: Context): GameOnNumberOfWordsHistoryStorage {
        return GameOnNumberOfWordsHistoryStorage(context)
    }

    @Provides
    fun provideClassicGameModesHistoryList(@ApplicationContext context: Context): ClassicGameModesHistoryList {
        return ClassicGameModesHistoryList(
            GameOnTimeHistoryStorage(context).get(),
            GameOnNumberOfWordsHistoryStorage(context).get()
        )
    }

    @Provides
    @Singleton
    fun provideAchievementsProgressStorage(@ApplicationContext context: Context): AchievementsProgressStorage {
        return AchievementsProgressStorage.SharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideAchievementsList(@ApplicationContext context: Context): AchievementsList {
        return AchievementsList(context)
    }
}