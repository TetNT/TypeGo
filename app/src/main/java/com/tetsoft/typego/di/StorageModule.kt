package com.tetsoft.typego.di

import android.content.Context
import com.tetsoft.typego.data.history.GameHistory
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.history.RandomWordsHistoryStorage
import com.tetsoft.typego.storage.history.OwnTextGameHistoryStorage
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
    fun provideRandomWordsHistoryStorage(@ApplicationContext context: Context): RandomWordsHistoryStorage.SharedPreferences {
        return RandomWordsHistoryStorage.SharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideRandomWordsHistoryStorageMock() : RandomWordsHistoryStorage.Mock {
        return RandomWordsHistoryStorage.Mock()
    }

    @Provides
    @Singleton
    fun provideOwnTextGameHistoryStorage(@ApplicationContext context: Context) : OwnTextGameHistoryStorage.SharedPreferences {
        return OwnTextGameHistoryStorage.SharedPreferences(context)
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

    @Provides
    @Singleton
    fun provideGameHistory(@ApplicationContext context: Context) : GameHistory.Standard {
        return GameHistory.Standard(
            RandomWordsHistoryStorage.SharedPreferences(context).get(),
            OwnTextGameHistoryStorage.SharedPreferences(context).get()
        )
    }

}