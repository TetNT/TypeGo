package com.tetsoft.typego.achievements.di

import android.content.Context
import com.tetsoft.typego.achievements.data.AchievementsProgressStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AchievementsProgressModule {

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