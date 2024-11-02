package com.tetsoft.typego.achievements.di

import com.tetsoft.typego.achievements.data.AchievementsProgressStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AchievementsProgressStorageModule {

    @Binds
    fun bindAchievementsProgressStorage(achievementsProgressStorage: AchievementsProgressStorage.SharedPreferences) : AchievementsProgressStorage
}