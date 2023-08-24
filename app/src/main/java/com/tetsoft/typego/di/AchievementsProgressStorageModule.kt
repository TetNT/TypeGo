package com.tetsoft.typego.di

import com.tetsoft.typego.storage.AchievementsProgressStorage
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