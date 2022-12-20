package com.tetsoft.typego.di

import android.content.Context
import com.tetsoft.typego.BuildConfig
import com.tetsoft.typego.data.achievement.AchievementsList
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.GameResultsDataSource
import com.tetsoft.typego.storage.GameResultListStorage
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
    fun provideGameResultListStorage(@ApplicationContext context: Context) : GameResultsDataSource {
        //if (BuildConfig.DEBUG) return GameResultsDataSource.Mock()
        return GameResultListStorage(context, KEY_RESULT_LIST_STORAGE)
    }

    @Provides
    @Singleton
    fun provideAchievementsProgressStorage(@ApplicationContext context: Context) : AchievementsProgressStorage {
        return AchievementsProgressStorage(context)
    }

    @Provides
    @Singleton
    fun provideAchievementsList(@ApplicationContext context: Context) : AchievementsList {
        return AchievementsList(context)
    }

    companion object {
        private const val KEY_RESULT_LIST_STORAGE = "result_list_storage"
    }
}