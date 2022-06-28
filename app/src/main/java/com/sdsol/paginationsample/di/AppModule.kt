package com.sdsol.paginationsample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class AppModule {
    @Provides
    @Singleton
    @StagingBaseUrl
    fun provideStagingBaseURL() = "https://staging3.sdsol.com/Clubby/api/v1/"

    @Provides
    @Singleton
    @LiveBaseUrl
    fun provideLiveBaseURL() = "https://appserver.clubby.pro/api/v1/"


}