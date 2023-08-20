package com.tetsoft.typego.di

import android.content.Context
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
    fun provideGameOnTimeHistoryStorage(@ApplicationContext context: Context): GameOnTimeHistoryStorage.SharedPreferences {
        return GameOnTimeHistoryStorage.SharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideGameOnTimeHistoryMock() : GameOnTimeHistoryStorage.Mock {
        return GameOnTimeHistoryStorage.Mock()
    }

    @Provides
    @Singleton
    fun provideGameOnNumberOfWordsHistoryStorage(@ApplicationContext context: Context): GameOnNumberOfWordsHistoryStorage.SharedPreferences {
        return GameOnNumberOfWordsHistoryStorage.SharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideGameOnNumberOfWordsHistoryMock() : GameOnNumberOfWordsHistoryStorage.Mock {
        return GameOnNumberOfWordsHistoryStorage.Mock()
    }

    @Provides
    @Singleton
    fun provideAchievementsProgressStorageMock() : AchievementsProgressStorage.Mock {
        return AchievementsProgressStorage.Mock()
    }

    @Provides
    @Singleton
    fun provideAchievementsProgressStorage(@ApplicationContext context: Context) : AchievementsProgressStorage.SharedPreferences {
        return AchievementsProgressStorage.SharedPreferences(context)
    }

}